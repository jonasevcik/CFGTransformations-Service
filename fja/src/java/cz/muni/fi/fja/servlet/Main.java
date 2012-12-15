/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fja.servlet;

import cz.muni.fi.cfg.forms.Transformations;
import cz.muni.fi.cfg.grammar.ContextFreeGrammar;
import cz.muni.fi.cfg.parser.Parser;
import cz.muni.fi.cfg.parser.ParserException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xsevci11
 */
public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        ContextFreeGrammar cfg = null;
        ContextFreeGrammar b = null;
        try {
            cfg = parser.parse("S' -> \\e | DA | DAS | dbE,A -> a | cDdB,C -> d,D -> cCAc,E -> CaaBE,S -> DA | DAS | dbE");
            b = parser.parse("S -> DAS | \\e | dbE,A -> cDdB | a,C -> d,D -> cCAc,E -> CaaBE");
        } catch (ParserException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(cfg.getInitialNonTerminal());
        System.out.println(cfg.getNonTerminals());
        System.out.println(cfg.getTerminals());
        System.out.println(cfg.getRules());
        Transformations transformate = new Transformations();

        ContextFreeGrammar cfg2 = transformate.removeSimpleRules(b);
        System.out.println(b.getInitialNonTerminal());
        System.out.println(b.getNonTerminals());
        System.out.println(cfg2.getInitialNonTerminal());
        System.out.println(cfg2.getNonTerminals());
        System.out.println(cfg2.getTerminals());
        System.out.println(cfg2.getRules());
    }
}
