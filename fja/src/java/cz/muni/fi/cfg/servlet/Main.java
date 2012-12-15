/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.cfg.servlet;

import cz.muni.fi.cfg.forms.FormChecker;
import cz.muni.fi.cfg.forms.TransformationException;
import cz.muni.fi.cfg.forms.Transformations;
import cz.muni.fi.cfg.grammar.ContextFreeGrammar;
import cz.muni.fi.cfg.parser.Parser;
import cz.muni.fi.cfg.parser.ParserException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author NICKT
 */
public class Main {

    public static void main(String[] args) throws ParserException, TransformationException {
        Parser p = new Parser();
        ContextFreeGrammar a = p.parse("S -> Cda | ca,D -> B,E -> Ad | C | Ca | Dd | d");
        ContextFreeGrammar b = p.parse("S->ACda|ca, A->\\e, D->B|A, E->DAd|Ca|C");
//        ContextFreeGrammar c = p.parse("S->SbA|\\e|CED, A->cbS, D->aDbE|CA, E->BB|Sa");
        Transformations t = new Transformations();
        ContextFreeGrammar c = t.removeEps(b);
//
//        ContextFreeGrammar b = p.parse("S -> A | B | b,A -> B,B -> A,C -> a | D,D -> a | C");
//        List<String> s = p.orderingOfNonTerminals("S->bAeaB|eAcda, A->a|baD|deacb, B->EB|Debe, D->c|cAcB|Be, E->bCcC");
//        List<String> u = p.orderingOfNonTerminals("S -> ac' | ac'D'c'e'a' | ac'c'e'a' | cDc'EEa' | cDc'EEa'D'c'e'a' | cDc'EEa'c'e'a',A -> ac'D'c'e'c'd' | ac'c'e'c'd' | cDc'EEa'D'c'e'c'd' | cDc'EEa'c'e'c'd' | cDc'EEc'd',C -> ac'D'c'e' | ac'c'e' | cDc'EE | cDc'EEa'D'c'e' | cDc'EEa'c'e',D -> ac' | ac'D' | cDc'EEa' | cDc'EEa'D',D' -> ce'a' | ce'a'D',E -> ab'DA | ac'D'c'e'Db'c' | ac'c'e'Db'c' | cDc'EEDb'c' | cDc'EEa'D'c'e'Db'c' | cDc'EEa'c'e'Db'c' | eb'c',a' -> a,b' -> b,c' -> c,d' -> d,e' -> e");


//        ContextFreeGrammar d = t.transformToGNF(b, u);
//        System.out.println(a);
//        System.out.println(a.getTerminals());
//        System.out.println(a.getNonTerminals());
//        Map<ContextFreeGrammar , List[]> m = t.removeLeftRecursion(b, s);
//        Set<ContextFreeGrammar> set = m.keySet();
//
//        ContextFreeGrammar n = null;
//        for (ContextFreeGrammar c : set) {
//            n = c;
//        }
//        FormChecker f = new FormChecker();
        System.out.println(a);
        System.out.println();
        System.out.println(c);
        System.out.println(a.getNonTerminals());
        System.out.println(c.getNonTerminals());
        System.out.println(a.getTerminals());
        System.out.println(c.getTerminals());
        System.out.println(a.getRulesArray());
        c.mapToArrayMap();
        System.out.println(c.getRulesArray());
//        boolean bool = a.equals(b);
//        System.out.println(bool);
//        System.out.println(c);
//        System.out.println(d);
        System.out.println(a.equals(c));
    }
}
