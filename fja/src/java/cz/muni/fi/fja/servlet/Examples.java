package cz.muni.fi.fja.servlet;

public class Examples implements java.io.Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 3489208086076695793L;
  private static final String[] names = new String[] { "Minimalizace",
      "Ztotalneni", "Minimalizace a kanonizace", "Kanonizace",
      "Ztotalneni a kanonizace", "Odstraneni epsilon kroku",
      "Vytvoreni NFA s epsilon kroky", "Reg.gramatika", "Reg.gramatika dle RE",
      "Regularni vyraz", "Automaticke rozeznani modelu" };
  private int example;

  public Examples() {
    example = -1;
  }

  public String[] getNames() {
    return names;
  }

  public int getCount() {
    return names.length;
  }

  public void setEx(int e) {
    example = e;
    /*
     * if (e != null) { example = Integer.valueOf(e); } else { example = -1; }
     */
  }

  public int getEx() {
    return example;
  }

  public String getText() {
    switch (example) {
      case (0):
        return "Student ma za ukol najit minimalni automat "
            + "pro jazyk vsech slov nad abecedou {a,b} obsahujicich "
            + "podslovo \"abb\". Vyucujici tento jazyk popise libovolnym "
            + "formalismem (napr. pomoci NFA), jako pozadavek na odpoved "
            + "zvoli MIN a vlozi odpoved studenta. Kliknutim na \"Vyhodnotit\" "
            + "zjistime, ze nize vlozena odpoved studenta je spravna.";
      case (1):
        return "Student ma za ukol vytvorit totalni automat ekvivalentni predloze. "
            + "Vyucujici tento jazyk popise libovolnym formalismem (napr. primo danym DFA), "
            + "jako pozadavek na odpoved zvoli TOT a vlozi odpoved studenta. "
            + "Kliknutim na \"Vyhodnotit\" zjistime, ze vlozena odpoved studenta je spravna.<br>"
            + "<pre>(X,a)=Y (Y,b)=Z (Z,c)=Z F={Z}</pre>";
      case (2):
        return "Student ma za ukol najit minimalni konecny automat v kanonizovane forme "
            + "pro zadany DFA. Vyucijici tento jazyk popise napr.danym DFA a "
            + "jako odpoved studenta zvoli MIC a vlozi jeho odpoved. Kliknutim na "
            + "\"Vyhodnotit\" zjistime, ze odpoved je spravna. <br>\n"
            + "<center><table class=\"example\">\n"
            + "<tr><th width=50>stav</th><th width=50>a</th><th width=50>b</th></tr>\n"
            + "<tr><td class=right>&lt;-&gt;1</td><td>2</td><td>3</td></tr>\n"
            + "<tr><td class=right>2</td><td>4</td><td>5</td></tr>\n"
            + "<tr><td class=right>&lt;-3</td><td>-</td><td>1</td></tr>\n"
            + "<tr><td class=right>&lt;-4</td><td>6</td><td>6</td></tr>\n"
            + "<tr><td class=right>&lt;-5</td><td>4</td><td>7</td></tr>\n"
            + "<tr><td class=right>&lt;-6</td><td>6</td><td>9</td></tr>\n"
            + "<tr><td class=right>&lt;-7</td><td>4</td><td>7</td></tr>\n"
            + "<tr><td class=right>&lt;-8</td><td>2</td><td>5</td></tr>\n"
            + "<tr><td class=right>&lt;-9</td><td>6</td><td>9</td></tr>\n"
            + "</table></center>";
      case (3):
        return "Student ma za ukol vytvorit kanonicky automat ekvivalentni "
            + "zadanemu EFA. Vyucujici tento jazyk popise libovolnym "
            + "formalismem (napr. zada primo onen EFA), jako pozadavek na odpoved "
            + "zvoli CAN a vlozi odpoved studenta. Kliknutim na \"Vyhodnotit\" "
            + "zjistime, ze nize vlozena odpoved studenta je spravna.<br>"
            + "<center><table class=\"example\">\n"
            + "<tr><th width=50>stav</th><th width=50>m</th><th width=50>n</th></tr>\n"
            + "<tr><td class=right>-&gt;A</td><td>C,D</td><td>-</td></tr>\n"
            + "<tr><td class=right>&lt;-B</td><td>-</td><td>C</td></tr>\n"
            + "<tr><td class=right>C</td><td>A,D</td><td>-</td></tr>\n"
            + "<tr><td class=right>D</td><td>-</td><td>B</td></tr>\n"
            + "</table></center>";
      case (4):
        return "Student ma za ukol vytvorit totalni kanonicky automat ekvivalentni "
            + "predloze. Vyucujici tento jazyk popise libovolnym "
            + "formalismem (napr. zada primo onen DFA), jako pozadavek na odpoved "
            + "zvoli TOC a vlozi odpoved studenta. Kliknutim na \"Vyhodnotit\" "
            + "zjistime, ze nize vlozena odpoved studenta je spravna.<br>"
            + "<center><table class=\"example\">\n"
            + "<tr><th width=50>stav</th><th width=50>x</th><th width=50>y</th></tr>\n"
            + "<tr><td class=right>-&gt;A</td><td>D</td><td>B</td></tr>\n"
            + "<tr><td class=right>&lt;-B</td><td>C</td><td>-</td></tr>\n"
            + "<tr><td class=right>C</td><td>-</td><td>A</td></tr>\n"
            + "<tr><td class=right>&lt;-D</td><td>C</td><td>-</td></tr>\n"
            + "</table></center>";
      case (5):
        return "Student ma za ukol odstranit ze zadaneho EFA odstranit "
            + "epsilon kroky. Vyucujici tento jazyk popise libovolnym formalismem "
            + "(napr. pomoci daneho EFA), jako pozadavek na odpoved "
            + "zvoli NFA a vlozi odpoved studenta. Kliknutim na \"Vyhodnotit\" "
            + "zjistime, ze nize vlozena odpoved studenta je spravna.<br>"
            + "<center><table class=\"example\">\n"
            + "<tr><th width=50>stav</th><th width=60>a</th><th width=60>b</th><th width=60>\\e</th><th width=60>b</th></tr>\n"
            + "<tr><td class=right>-&gt;1</td><td>1,2</td><td>-</td><td>-</td><td>2</td></tr>\n"
            + "<tr><td class=right>2</td><td>5</td><td>3,5</td><td>-</td><td>-</td></tr>\n"
            + "<tr><td class=right>3</td><td>-</td><td>6</td><td>-</td><td>-</td></tr>\n"
            + "<tr><td class=right>4</td><td>-</td><td>4</td><td>-</td><td>1,5</td></tr>\n"
            + "<tr><td class=right>5</td><td>5</td><td>-</td><td>3</td><td>6</td></tr>\n"
            + "<tr><td class=right>&lt;-6</td><td>-</td><td>-</td><td>3,6</td><td>2</td></tr>\n"
            + "</table></center>";
      case (6):
        return "Student ma za ukol vytvorit NFA s epsilon kroky ekvivalentni zadane reg.gramatice. "
            + "Vyucujici tento jazyk popise libovolnym formalismem (napr. primo danou gramatikou GRA), "
            + "jako pozadavek na odpoved zvoli EFA a vlozi odpoved studenta. "
            + "Kliknutim na \"Vyhodnotit\" zjistime, ze vlozena odpoved studenta je spravna.<br>"
            + "<center><table><tr><td><pre class=left>S -&gt; aA | bC | a | \\e\n"
            + "A -&gt; bB | aA | b | c\n"
            + "B -&gt; aB | bC | aC | cA | c\n"
            + "C -&gt; a| b | aA | bB</pre></td></tr></table></center>";
      case (7):
        return "Student ma za ukol najit reg. gramatiku pro jazyk "
            + "nad abecedou {0,1}, akceptujici slova, kde je pocet znaku \"0\" "
            + "i pocet znaku \"1\" delitelny dvemi. Vyucujici tento jazyk popise "
            + "libovolnym formalismem (napr. pomoci DFA), jako pozadavek na odpoved "
            + "zvoli REG a vlozi odpoved studenta. Kliknutim na \"Vyhodnotit\" "
            + "zjistime, ze nize vlozena odpoved studenta je spravna.";
      case (8):
        return "Student ma za ukol najit reg. gramatiku pro zadany regularni vyraz. "
            + "Vyucujici tento jazyk popise libovolnym formalismem (napr. primo dany RE), "
            + "jako pozadavek na odpoved zvoli GRA a vlozi odpoved studenta. Kliknutim na "
            + "\"Vyhodnotit\" zjistime, ze nize vlozena odpoved studenta je spravna.<br>"
            + "<pre>re = ab^*</pre>";
      case (9):
        return "Student ma za ukol najit reg. vyraz pro jazyk nad abecedou (x,y) "
            + "akceptujici vsechna slova s poctem znaku delitelnymi 3. "
            + "Vyucujici tento jazyk popise libovolnym formalismem (napr. pomoci DFA), "
            + "jako pozadavek na odpoved zvoli REG a vlozi odpoved studenta. "
            + "Kliknutim na \"Vyhodnotit\" zjistime, ze nize vlozena odpoved studenta je spravna.";
      case (10):
        return "Student ma za ukol vytvorit pomoci libovolne formalizace (DFA,NFA,EFA,GRA,RE) "
            + "model akceptujici jazyk nad abecedou {a,b,c}, kde soucet znaku {b,c} je lichy. "
            + "Vyucujici tento jazyk popise libovolnym formalismem (napr. pomoci DFA), "
            + "jako pozadavek na odpoved zvoli ALL a vlozi odpoved studenta. "
            + "Kliknutim na \"Vyhodnotit\" zjistime, ze nize vlozena odpoved studenta je spravna.";
      default:
        return "";
    }
  }

  public String getTeacherType() {
    switch (example) {
      case (0): return "EFA";
      case (1): return "DFA";
      case (2): return "DFA";
      case (3): return "EFA";
      case (4): return "DFA";
      case (5): return "EFA";
      case (6): return "GRA";
      case (7): return "DFA";
      case (8): return "REG";
      case (9): return "DFA";
      case (10): return "DFA";
      default: return "";
    }
  }

  public String getTeacher() {
    switch (example) {
      case (0):
        return "(S,a)={S,A} (S,b)={S}\n" + "(A,b)={AB}\n" + "(AB,b)={ABB}\n"
            + "(ABB,a)={ABB} (ABB,b)={ABB}\n" + "F={ABB}";
      case (1):
        return "(X,a)=Y (Y,b)=Z (Z,c)=Z\nF={Z}";
      case (2):
        return "(1,a)=2 (1,b)=3\n" + "(2,a)=4 (2,b)=5\n" + "(3,b)=1\n"
            + "(4,a)=6 (4,b)=6\n" + "(5,a)=4 (5,b)=7\n" + "(6,a)=6 (6,b)=9\n"
            + "(7,a)=4 (7,b)=7\n" + "(8,a)=2 (8,b)=5\n" + "(9,a)=6 (9,b)=9\n"
            + "F={1,3,4,5,6,7,9}\n";
      case (3):
        return "" + "(A,m)={C,D}\n" + "(B,n)={C}\n" + "(C,m)={A,D}\n"
            + "(D,n)={B}\n" + "F={B}";
      case (4):
        return "(A,x)=D (A,y)=B\n" + "(B,x)=C\n" + "(C,y)=A\n" + "(D,x)=C\n"
            + "F={B,D}";
      case (5):
        return "(1,a)={1,2} (1,\\e)={2}\n" + "(2,a)={5} (2,b)={3,5}\n"
            + "(3,b)={6}\n" + "(4,b)={4} (4,\\e)={1,5}\n"
            + "(5,a)={5} (5,c)={3} (5,\\e)={6}\n" + "(6,c)={3,6} (6,\\e)={2}\n"
            + "F={6}";
      case (6):
        return "S -> aA| bC| a|\\e\n" + "A -> bB| aA| b | c \n"
            + "B -> aB| bC| aC| cA| c\n" + "C -> a | b | aA| bB";
      case (7):
        return "(QQ,0)=0Q(QQ,1)=Q1\n" + "(0Q,0)=QQ(0Q,1)=01\n"
            + "(Q1,0)=01(Q1,1)=QQ\n" + "(01,0)=Q1(01,1)=0Q\nF={QQ}";
      case (8):
        return "ab^*";
      case (9):
        return "(A,x)=B (A,y)=B\n" + "(B,x)=C (B,y)=C\n" + "(C,x)=A (C,y)=A\n"
            + "F={A} ";
      case (10):
        return "(A,a)=A (A,b)=B (A,c)=B \n" + "(B,a)=B (B,b)=A (B,c)=A \n"
            + "F={B}\n";
      default:
        return "";
    }
  }

  public String getStudentType() {
    switch (example) {
      case (0): return "MIN";
      case (1): return "TOT";
      case (2): return "MIC";
      case (3): return "CAN";
      case (4): return "TOC";
      case (5): return "NFA";
      case (6): return "EFA";
      case (7): return "GRA";
      case (8): return "GRA";
      case (9): return "REG";
      case (10): return "ALL";
      default: return "";
    }
  }

  public String getStudent() {
    switch (example) {
      case (0):
        return "(q0,a)=q1 (q0,b)=q0\n" + "(q1,a)=q1 (q1,b)=q2\n"
            + "(q2,a)=q1 (q2,b)=q3\n" + "(q3,a)=q3 (q3,b)=q3\n" + "F={q3}";
      case (1):
        return "(X,a)=Y (X,b)=N (X,c)=N\n" + "(Y,a)=N (Y,b)=Z (Y,c)=N\n"
            + "(Z,a)=N (Z,b)=N (Z,c)=Z\n" + "(N,a)=N (N,b)=N (N,c)=N\n" + "F={Z}";
      case (2):
        return "(A,a)=B (A,b)=C\n" + "(B,a)=D (B,b)=D\n" + "(C,a)=E (C,b)=A\n"
            + "(D,a)=D (D,b)=D\n" + "(E,a)=E (E,b)=E\n" + "F={A,C,D}";
      case (3):
        return "" + "(A,m)=B\n" + "(B,m)=C (B,n)=D\n" + "(C,m)=B (C,n)=D\n"
            + "(D,n)=E\n" + "(E,m)=C\n" + "F={D}";
      case (4):
        return "" + "(A,x)=B (A,y)=C\n" + "(B,x)=D (B,y)=E\n"
            + "(C,x)=D (C,y)=E\n" + "(D,x)=E (D,y)=A\n" + "(E,x)=E (E,y)=E\n"
            + "F={B,C}";
      case (5):
        return "(1,a)={1,2,5,6} (1,b)={2,3,5,6}\n"
            + "(2,a)={2,5,6} (2,b)={2,3,5,6}\n" + "(3,b)={2,6}\n"
            + "(4,a)={1,2,5,6} (4,b)={1,2,3,4,5,6} (4,c)={2,3,6}\n"
            + "(5,a)={2,5,6} (5,b)={2,3,5,6} (5,c)={2,3,6}\n"
            + "(6,a)={2,5,6} (6,b)={2,3,5,6} (6,c)={2,3,6}\n" + "F={6}";
      case (6):
        return "(S,a)={A,N} (S,b)={C}\n" + "(A,a)={A} (A,b)={B,N} (A,c)={N}\n"
            + "(B,a)={B,C} (B,b)={C} (B,c)={A,N}\n" + "(C,a)={A,N} (C,b)={B,N}\n"
            + "F={S, N}";
      case (7):
        return "S -> \\e | 0<0> | 1<1>\n" + "<0> -> 0 | 0<0011> | 1<01>\n"
            + "<1> -> 1 | 1<0011> | 0<01>\n" + "<01> -> 0<1> | 1<0>\n"
            + "<0011> -> 0<0> | 1<1>";
      case (8):
        return "S -> \\e | aB \n" + "B -> bA | b \n" + "A -> aB";
      case (9):
        return "((x+y)(x+y)(x+y))*";
      case (10):
        return "(a+(b+c).a^*.(b+c))^*.(b+c).a^*";
      default: return "";
    }
  }
}
