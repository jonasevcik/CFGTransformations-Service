package cz.muni.fi.fja.grammar;

import cz.muni.fi.fja.DeviceAbstract;
import cz.muni.fi.fja.common.Alphabet;
import cz.muni.fi.fja.common.Control;
import cz.muni.fi.fja.common.InStream;
import cz.muni.fi.fja.common.ModelError;
import cz.muni.fi.fja.common.Rule;
import cz.muni.fi.fja.fa.EFA;

public class Grammar extends DeviceAbstract {
  private Rule[] controls;
  private Alphabet[] alphabets;

  /*
   * 0 - CAN 1 - MIN 2 - DFA 3 - EFR 4 - NFA 5 - GRA 6 - REG
   */

  Grammar(ModelError error) {
    super(-1);
    setError(error);
  }

  public Grammar(InStream is) {
    super(5);
    GrammarReader grammar = new GrammarReader(is);
    setError(grammar.getError());
    controls = grammar.getAllControl();
    alphabets = grammar.getAllAlphabet();
    controlCount = controls.length;
    alphabetCount = alphabets.length;
  }

  public EFA makeEFA() {
    return createNFA();
  }

  public EFA makeNFA() {
    return createNFA();
  }

  private EFA createNFA() {
    if (isError()) {
      return new EFA(new NFAFromGrammarReader(getError()));
    }

    Rule[] newControls = new Rule[controlCount + 1];

    String sFinal = "qF";
    for (int i = 0; i < controlCount; i++) {
      if (controls[i].getControl().toString().equals(sFinal)) {
        sFinal += "F";
        i = 0;
      }
    }
    Control f = new Control(sFinal);
    newControls[controlCount] = new RuleGrammar(f);
    Control[] newFinals;
    if (controls[0].containsEpsilon()) {
      newFinals = new Control[] { controls[0].getControl(), f };
    } else {
      newFinals = new Control[] { f };
    }
    for (int i = 0; i < controlCount; i++) {
      newControls[i] = controls[i].convertToNFA(f);
    }

    return new EFA(new NFAFromGrammarReader(newControls, checkAlphabets(),
        newFinals));
  }

  private Alphabet[] checkAlphabets() {
    boolean epsilon = false;
    for (int i = 0; i < alphabetCount; i++) {
      if (alphabets[i].isEpsilon()) {
        epsilon = true;
        break;
      }
    }
    Alphabet[] a;
    if (epsilon) {
      a = new Alphabet[alphabetCount - 1];
    } else {
      a = new Alphabet[alphabetCount];
    }
    for (int i = 0, j = 0; i < alphabetCount; i++) {
      if (!alphabets[i].isEpsilon()) {
        a[j] = alphabets[i];
        j++;
      }
    }
    return a;
  }

  protected String deviceToString(boolean inOneRow) {
    StringBuffer sb = new StringBuffer(13 + (13 * alphabetCount + 1)
        * controlCount);

    for (int i = 0; i < controlCount; i++) {
      sb.append(controls[i]);
      if (!inOneRow) {
        sb.append("\n");
      } else {
        sb.append(",");
      }
    }
    return sb.toString();
  }

}
