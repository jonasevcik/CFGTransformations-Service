package cz.muni.fi.fja.reg;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cz.muni.fi.fja.DeviceAbstract;
import cz.muni.fi.fja.common.Alphabet;
import cz.muni.fi.fja.common.Control;
import cz.muni.fi.fja.common.InStream;
import cz.muni.fi.fja.common.ModelError;
import cz.muni.fi.fja.common.Rule;
import cz.muni.fi.fja.common.Symbol;
import cz.muni.fi.fja.fa.EFA;
import cz.muni.fi.fja.fa.RuleEFA;

public class Reg extends DeviceAbstract {
  public static final String INITIATE_CONTROL = "x";
  public static final String FINAL_CONTROL = "y";
  private List<Rule> controls;
  private Alphabet[] alphabets;
  private Symbol[] postfix;
  private int postfixCount;

  private static final Alphabet EPSILON = Alphabet.getEpsilon();

  public Reg(InStream is) {
    super(6);
    RegReader reg = new RegReader(is);
    setError(reg.getError());
    postfix = reg.getPostfix();
    alphabets = reg.getAllAlphabet();
    alphabetCount = alphabets.length;
    postfixCount = postfix.length;
    controlCount = 0;
  }

  public EFA makeEFA() {
    return createEFA();
  }

  private EFA createEFA() {
    if (isError()) {
      return new EFA(new EFAFromRegReader(getError()));
    }

    Stack<TempFA> stack = new Stack<TempFA>();
    controls = new ArrayList<Rule>(2 * postfixCount + 2);
    controls.add(new RuleEFA(new Control(INITIATE_CONTROL)));
    controls.add(new RuleEFA(new Control(FINAL_CONTROL)));
    controlCount = 1;

    for (int i = 0; i < postfixCount; i++) {
      if (postfix[i].isAlphabet()) {
        stack.push(createFA((Alphabet) postfix[i]));
      } else {
        Operator op = (Operator) postfix[i];
        if (!checkStack(stack, op.numberOfArgs())) {
          assert isError();
          return new EFA(new EFAFromRegReader(getError()));
        }
        if (op == Operator.CONCAT || op == Operator.CLOSE_CONCAT) {
          TempFA fa2 = stack.pop();
          TempFA fa1 = stack.pop();
          stack.push(concatFA(fa1, fa2));
        } else if (op == Operator.UNION) {
          TempFA fa2 = stack.pop();
          TempFA fa1 = stack.pop();
          stack.push(unionFA(fa1, fa2));
        } else if (op == Operator.ITERATOR) {
          stack.push(iterateFA(stack.pop()));
        } else if (op == Operator.PLUS_ITERATOR) {
          stack.push(plusIterateFA(stack.pop()));
        }

      }
    }
    if (stack.size() == 0) {
      setError(ModelError.notFoundRE());
    } else if (stack.size() > 1) {
      setError(ModelError.tooMuchArguments());
    }
    if (isError()) {
      return new EFA(new EFAFromRegReader(getError()));
    } else {
      TempFA fa = stack.pop();
      controls.get(0).add(EPSILON, fa.initRule);
      fa.finalRule.add(EPSILON, controls.get(1));
      return new EFA(new EFAFromRegReader(controls.toArray(new Rule[0]),
          alphabets, controls.get(1)));
    }
  }

  private Rule createRule() {
    Rule r = new RuleEFA(new Control("q" + controlCount));
    controls.add(r);
    controlCount++;
    return r;
  }

  private TempFA createFA(Alphabet a) {
    Rule r1 = createRule();
    Rule r2 = createRule();
    if (!a.isEmptySet()) {
      r1.add(a, r2);
    }
    return new TempFA(r1, r2);
  }

  private TempFA unionFA(TempFA fa1, TempFA fa2) {
    Rule r1 = createRule();
    Rule r2 = createRule();
    r1.add(EPSILON, fa1.initRule);
    r1.add(EPSILON, fa2.initRule);
    fa1.finalRule.add(EPSILON, r2);
    fa2.finalRule.add(EPSILON, r2);
    return new TempFA(r1, r2);
  }

  private TempFA concatFA(TempFA fa1, TempFA fa2) {
    fa1.finalRule.add(EPSILON, fa2.initRule);
    return new TempFA(fa1.initRule, fa2.finalRule);
  }

  private TempFA iterateFA(TempFA fa) {
    Rule r1 = createRule();
    Rule r2 = createRule();
    Rule r3 = createRule();
    r1.add(EPSILON, r2);
    r2.add(EPSILON, r3);
    r2.add(EPSILON, fa.initRule);
    fa.finalRule.add(EPSILON, r2);
    return new TempFA(r1, r3);
  }

  private TempFA plusIterateFA(TempFA fa) {
    Rule r1 = createRule();
    Rule r2 = createRule();
    r1.add(EPSILON, fa.initRule);
    fa.finalRule.add(EPSILON, fa.initRule);
    fa.finalRule.add(EPSILON, r2);
    return new TempFA(r1, r2);
  }

  public boolean checkStack(Stack s, int i) {
    if (s.size() >= i) {
      return true;
    } else {
      setError(ModelError.fewArguments());
      return false;
    }
  }

  public String deviceToString(boolean inOneRow) {
    Stack<String> stringStack = new Stack<String>();
    Stack<Operator> opStack = new Stack<Operator>();
    for (int i = 0; i < postfixCount && !isError(); i++) {
      if (postfix[i].isAlphabet()) {
        stringStack.push(postfix[i].toRegString());
        opStack.push(Operator.CLOSE_CONCAT);
      } else {
        Operator o = (Operator) postfix[i];
        if (!checkStack(stringStack, o.numberOfArgs())) {
          break;
        }
        if (o.isBinary()) {
          String bracket = "";
          String secondArg = "";
          Operator pomO = opStack.pop();
          if (o.isHigherThan(pomO)) {
            secondArg = "(";
            bracket = ")";
          }
          secondArg += stringStack.pop() + bracket;
          bracket = "";
          String firstArg = "";
          pomO = opStack.pop();
          if (o.isHigherThan(pomO) && pomO.isBinary()) {
            firstArg = "(";
            bracket = ")";
          }
          firstArg += stringStack.pop() + bracket;
          stringStack.push(firstArg + o.toRegString() + secondArg);
          opStack.push(o);
        } else {
          String bracket = "";
          String s = "";
          Operator pomO = opStack.pop();
          if (o.isHigherThan(pomO) && pomO.isBinary()) {
            bracket = ")";
            s = "(";
          }
          s += stringStack.pop() + bracket;
          stringStack.push(s + o.toRegString());
          opStack.push(o);
        }
      }
    }
    if (stringStack.size() != 1) {
      setError(ModelError.tooMuchArguments());
    }
    if (isError()) {
      return getError().toString();
    } else {
      return stringStack.pop();
    }
  }

  private void setError(String s) {
    setError(ModelError.incorrectEnterDataError(s));
  }

  class TempFA {
    Rule initRule;
    Rule finalRule;

    TempFA(Rule r1, Rule r2) {
      initRule = r1;
      finalRule = r2;
    }
  }
}
