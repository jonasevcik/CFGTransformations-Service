package cz.muni.fi.fja.fa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import cz.muni.fi.fja.DeviceAbstract;
import cz.muni.fi.fja.common.Alphabet;
import cz.muni.fi.fja.common.Control;
import cz.muni.fi.fja.common.InStream;
import cz.muni.fi.fja.common.IntegerSortedSet;
import cz.muni.fi.fja.common.ModelError;
import cz.muni.fi.fja.common.ModelReader;
import cz.muni.fi.fja.common.Rule;

public class EFA extends DeviceAbstract {
  SortedSet<Integer>[][] tableRule;
  private Integer[] finalControl; // must be sorted
  private Alphabet[] alphabets;
  private Control[] controls;
  private int epsilon;

  EFA(SortedSet<Integer>[][] rules, Alphabet[] alphas, Control[] contrs,
      Integer[] finals, int e, int type) {
    super(type);
    tableRule = rules;
    alphabets = alphas;
    finalControl = finals;
    this.controls = contrs;
    epsilon = e;

    alphabetCount = alphabets.length;
    controlCount = tableRule.length;
  }

  EFA(ModelError error) {
    super(-1);
    setError(error);
  }

  public EFA(InStream is) {
    this(new EFAReader(is));
  }

  public EFA(ModelReader nfa) {
    super(4);
    setError(nfa.getError());
    if (!isError()) {
      alphabets = nfa.getAllAlphabet();
      Arrays.sort(alphabets);
      alphabetCount = alphabets.length;

      Rule[] rules = nfa.getAllControl();
      controlCount = rules.length;
      tableRule = new IntegerSortedSet[controlCount][alphabetCount];
      for (int i = 0; i < alphabetCount; i++)
        alphabets[i].setInt(i);
      for (int i = 0; i < controlCount; i++)
        rules[i].getControl().setInt(i);
      for (int i = 0; i < controlCount; i++) {
        Rule r = rules[i];
        for (Alphabet a : r.getAlphabet()) {
          Control[] pomC = r.getControl(a);
          SortedSet<Integer> pomI = new IntegerSortedSet();
          for (int k = 0, l = pomC.length; k < l; k++)
            pomI.add(pomC[k].getInt());
          tableRule[i][a.getInt()] = pomI;
        }
      }

      epsilon = -1;
      for (int i = 0; i < alphabetCount && epsilon < 0; i++) {
        if (alphabets[i].isEpsilon()) {
          epsilon = i;
        }
      }

      controls = new Control[controlCount];
      for (int i = 0; i < controlCount; i++) {
        controls[i] = rules[i].getControl();
      }

      Control[] finals = nfa.getAllFinal();
      finalControl = new Integer[finals.length];
      int h = 0;
      for (Control c : finals) {
        finalControl[h] = c.getInt();
        h++;
      }
      Arrays.sort(finalControl);
    }
  }

  public boolean containsEpsilon() {
    if (epsilon >= 0)
      return true;

    return false;
  }

  public DFA makeDFA() {
    return makeNFA().convertToDFA();
  }

  public EFA makeNFA() {
    return removeEpsilonSteps();
  }

  public EFA removeEpsilonSteps() {
    if (isError())
      return new EFA(getError());

    if (epsilon == -1)
      return this;

    SortedSet<Integer>[] nEpsilon = new IntegerSortedSet[controlCount];
    for (int countedState = 0; countedState < controlCount; countedState++) {
      if (nEpsilon[countedState] == null) {
        nEpsilon[countedState] = new IntegerSortedSet();
        nEpsilon[countedState].add(new Integer(countedState));
        getAvailableEpsilonState(nEpsilon, countedState, nEpsilon[countedState]);
      }
    }

    SortedSet<Integer>[][] newRules = new IntegerSortedSet[controlCount][alphabetCount - 1];
    for (int i = 0; i < controlCount; i++) {
      for (int j = 0, k = 0; j < alphabetCount; j++) {
        if (j != epsilon) {
          Set<Integer> temporarySet = new HashSet<Integer>();
          for (Integer n : nEpsilon[i]) {
            if (tableRule[n.intValue()][j] != null) {
              temporarySet.addAll(tableRule[n.intValue()][j]);
            }
          }
          if (!temporarySet.isEmpty()) {
            newRules[i][k] = new IntegerSortedSet();
            for (Integer n : temporarySet) {
              newRules[i][k].addAll(nEpsilon[n.intValue()]);
            }
          }
          k++;
        }
      }
    }
    Alphabet[] newAlphabets = new Alphabet[alphabetCount - 1];
    for (int i = 0, j = 0; i < alphabetCount; i++) {
      if (i != epsilon) {
        newAlphabets[j] = alphabets[i];
        j++;
      }
    }
    Integer[] newFinals = null;
    if (finalControl.length > 0) {
      if (finalControl[0] != 0) {
        for (int i = 0, l = finalControl.length; i < l; i++) {
          if (nEpsilon[0].contains(finalControl[i])) {
            newFinals = new Integer[finalControl.length + 1];
            newFinals[0] = new Integer(0);
            for (int j = 0; j < l; j++) {
              newFinals[j + 1] = finalControl[j];
            }
            break;
          }
        }
      }
      if (newFinals == null) {
        newFinals = new Integer[finalControl.length];
        for (int i = 0, l = finalControl.length; i < l; i++) {
          newFinals[i] = finalControl[i];
        }
      }

    } else {
      newFinals = new Integer[0];
    }
    return new EFA(newRules, newAlphabets, controls, newFinals, -1, 3);
  }

  private void getAvailableEpsilonState(SortedSet<Integer>[] s, int n,
      SortedSet<Integer> result) {
    // System.out.println("*********************");
    // System.out.println("getAvailableEpsState: akt.stav=" +
    // controls[n].toFAString() + " number=" + n);
    // System.out.println("aktualni SortedSet (result)" + result);
    if (tableRule[n][epsilon] != null) {
      if (result.addAll(tableRule[n][epsilon])) {
        for (Integer i : tableRule[n][epsilon]) {
          // System.out.println("getAvailableEpsState: akt.stav=" +
          // controls[n].toFAString() + " number=" + n);
          // System.out.println("aktualni SortedSet (result)" + result);
          if (s[i.intValue()] == null) {
            getAvailableEpsilonState(s, i.intValue(), result);
          } else if (s[i] != result) {
            result.addAll(s[i]);
          }
        }
      }
    }
  }

  public DFA convertToDFA() {
    if (isError())
      return new DFA(getError());

    List<Integer> newFinalSet = new ArrayList<Integer>();
    List<int[]> newRules = new ArrayList<int[]>();
    Integer blackHole = new Integer(controlCount);
    Map<NewState, NewState> newStateToInt = new HashMap<NewState, NewState>();
    List<NewState> intToNewState = new ArrayList<NewState>();
    NewState pomState = new NewState(new int[] { 0 }, 0);
    if (finalControl.length > 0)
      if (finalControl[0].intValue() == 0)
        newFinalSet.add(0);
    newStateToInt.put(pomState, pomState);
    intToNewState.add(pomState);

    for (int i = 0; i < intToNewState.size(); i++) {
      int[] actualRule = new int[alphabetCount];
      int[] actualState = intToNewState.get(i).getName();
      // number of ActualState is i
      for (int j = 0; j < alphabetCount; j++) {
        SortedSet<Integer> targetNewState = new IntegerSortedSet();
        for (int k = 0; k < actualState.length; k++) { // take all OldStates and
                                                       // add their Targetes to
                                                       // the Set
          if (actualState[k] == controlCount) { // controlCount is Number of
                                                // blackHole
            targetNewState.add(blackHole);
          } else {
            if (tableRule[actualState[k]][j] == null) {
              targetNewState.add(blackHole);
            } else {
              targetNewState.addAll(tableRule[actualState[k]][j]);
            }
          }
        }
        int[] targetNewStateArray = new int[targetNewState.size()];
        int m = 0;
        for (Integer n : targetNewState) {
          targetNewStateArray[m] = n.intValue();
          m++;
        }
        pomState = new NewState(targetNewStateArray, intToNewState.size());
        NewState pomState2 = newStateToInt.get(pomState);
        if (pomState2 == null) { // if this State doesnt exist = add its
          intToNewState.add(pomState);
          newStateToInt.put(pomState, pomState);
          if (isFinalAnyState(targetNewStateArray)) {
            newFinalSet.add(new Integer(pomState.getInt()));
          }
        } else {
          pomState = pomState2;
        }
        actualRule[j] = pomState.getInt();
      }
      newRules.add(actualRule);
    }

    int l = newRules.size();
    int[][] tempRule = new int[l][alphabetCount];
    for (int i = 0; i < l; i++) {
      tempRule[i] = newRules.get(i);
    }

    Control[] contros = new Control[l];
    Control blackHoleControl = null;
    l = controls.length;
    String s = "N";
    for (int i = 0; i < l; i++) {
      if (s.equals(controls[i].toString())) {
        s += "N";
        i = 0;
      }
    }
    blackHoleControl = new Control(s);
    l = intToNewState.size();
    for (int i = 0; i < l; i++) {
      int[] pom = intToNewState.get(i).getName();
      s = "[";
      boolean first = true;
      for (int j = 0, k = pom.length; j < k; j++) {
        if (first) {
          first = false;
        } else {
          s += "-";
        }
        if (pom[j] == blackHole) {
          s += blackHoleControl;
        } else {
          s += controls[pom[j]];
        }
      }
      contros[i] = new Control(s + "]");
    }

    int i = 0;
    int[] finalArray = new int[newFinalSet.size()];
    for (Integer n : newFinalSet) {
      finalArray[i] = n.intValue();
      i++;
    }

    return new DFA(tempRule, alphabets, contros, finalArray, 2);
  }

  private boolean isFinalAnyState(int[] state) {
    int indexFinal = 0, indexState = 0;
    int finalLength = finalControl.length, stateLength = state.length;
    while (indexFinal < finalLength && indexState < stateLength) {
      if (state[indexState] == finalControl[indexFinal]) {
        return true;
      } else if (state[indexState] < finalControl[indexFinal]) {
        indexState++;
      } else {
        indexFinal++;
      }

    }
    return false;
  }

  // private void printIntArray(int[] a) {
  // String s = "";
  // for (int i = 0; i < a.length; i++) {
  // s += a[i] + " ";
  // }
  // System.out.println(s);
  // }

  protected String deviceToString(boolean inOneRow) {
    StringBuffer sb = new StringBuffer(13 + (13 * alphabetCount + 1)
        * controlCount + 3 * finalControl.length);
    if (controls == null || controls.length == 0) {
      renameControls();
    }

    if (!inOneRow) {
      sb.append("init=" + controls[0].toFAString() + "\n");
    }
    for (int i = 0; i < controlCount; i++) {
      boolean added = false;
      for (int j = 0; j < alphabetCount; j++) {
        if (tableRule[i][j] != null) {
          added = true;
          boolean first = true;
          sb.append("(" + controls[i].toFAString() + ","
              + alphabets[j].toFAString() + ")={");
          for (Integer k : tableRule[i][j]) {
            if (first) {
              first = false;
            } else {
              sb.append(",");
            }
            sb.append(controls[k.intValue()].toFAString());
          }
          sb.append("}");
          if (!inOneRow) {
            sb.append(" ");
          }
        }
      }
      if (added && !inOneRow) {
        sb.append("\n");
      }
    }

    if (inOneRow) {
      sb.append(" ");
    }
    sb.append("F={");
    boolean first = true;
    for (int i = 0; i < finalControl.length; i++) {
      if (first) {
        first = false;
      } else {
        sb.append(",");
      }
      sb.append(controls[finalControl[i]].toFAString());
    }
    sb.append("}");
    if (!inOneRow) {
      sb.append("\n");
    }
    return sb.toString();
  }

  private void renameControls() {
    controls = new Control[controlCount];
    for (int i = 0, l = controlCount; i < l; i++) {
      if (l <= 26) {
        controls[i] = new Control(String.valueOf((char) ('A' + i)));
      } else {
        controls[i] = new Control(String.valueOf(i));
      }
    }
  }

}

class NewState {
  int[] name;
  int numberOfName;

  NewState(int[] a, int n) {
    name = a;
    numberOfName = n;
  }

  int[] getName() {
    return name;
  }

  int getInt() {
    return numberOfName;
  }

  public boolean equals(Object o) {
    if (!(o instanceof NewState))
      return false;

    NewState pom = (NewState) o;
    return Arrays.equals(name, pom.name);
  }

  public int hashCode() {
    return Arrays.hashCode(name);
  }

  public String toString() {
    String s = "";
    boolean first = true;
    for (int i = 0, l = name.length; i < l; i++) {
      if (first) {
        first = false;
      } else {
        s += "-";
      }
      s += name[i];
    }
    return s;
  }
}
