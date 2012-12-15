/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.cfg.conversions;

import cz.muni.fi.cfg.grammar.ContextFreeGrammar;
import cz.muni.fi.cfg.forms.Transformations;
import cz.muni.fi.cfg.forms.TransformationException;
import cz.muni.fi.cfg.forms.FormChecker;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NICKT
 */
public class CFGComparator {

    /**
     *
     * @param studentCFG
     * @param teacherCFG
     * @param ordering
     * @param type
     * @param mode
     * @return If the mode is simple returned array has only one value (true/false). If the mode is normal, it returns array with 4 values. Title, studentCFG, teacherCFG and answer.
     */
    public String[] compare(ContextFreeGrammar studentCFG, ContextFreeGrammar teacherCFG, List<String> ordering, TransformationTypes type, Modes mode) {
        String[] outputArray = null;
        FormChecker form = new FormChecker();
        Transformations transform = new Transformations();
        ContextFreeGrammar teacherTransformed = null;
        switch (type) {
            case NE1:
                if (!form.languageIsNotEmpty(teacherCFG)) {
                    if (!form.languageIsNotEmpty(studentCFG) && studentCFG.getInitialNonTerminal().equals("")) {
                        switch (mode) {
                            case simple:
                                String[] yes = {"true"};
                                outputArray = yes;
                                break;
                            case normal:
                                String[] answer = {"Porovnáno:", "NAG", "NAG", "Gramatiky jsou stejné a obě bez nenormovaných symbolů."};
                                outputArray = answer;
                                break;
                        }
                    } else {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), "NAG", "Studentova gramatika generuje neprázdný jazyk, narozdíl od gramatiky učitele. Mělo tedy být zadáno NAG (Not A Grammar)."};
                                outputArray = answer;
                                break;
                        }
                    }
                } else {
                    if (!form.languageIsNotEmpty(studentCFG) && studentCFG.getInitialNonTerminal().equals("")) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                try {
                                    teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                                } catch (TransformationException ex) {
                                    Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika generuje prázdný jazyk, narozdíl od gramatiky učitele."};
                                outputArray = answer;
                                break;
                        }
                    } else {
                        if (form.hasUnusefullSymbols(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    try {
                                        teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                                    } catch (TransformationException ex) {
                                        Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nenormované symboly."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            try {
                                teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                            } catch (TransformationException ex) {
                                Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if (!studentCFG.equals(teacherTransformed)) {
                                switch (mode) {
                                    case simple:
                                        String[] no = {"false"};
                                        outputArray = no;
                                        break;
                                    case normal:
                                        try {
                                            teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                                        } catch (TransformationException ex) {
                                            Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez nenormovaných symbolů, ale nejsou stejné."};
                                        outputArray = answer;
                                        break;
                                }
                            } else {
                                switch (mode) {
                                    case simple:
                                        String[] yes = {"true"};
                                        outputArray = yes;
                                        break;
                                    case normal:
                                        try {
                                            teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                                        } catch (TransformationException ex) {
                                            Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez nenormovaných symbolů a jsou stejné."};
                                        outputArray = answer;
                                        break;
                                }
                            }
                        }
                    }
                }
                break;
            case NE2:
                if (form.isNAG(studentCFG) && (form.isNAG(teacherCFG) || !form.languageIsNotEmpty(teacherCFG))) {
                    switch (mode) {
                        case simple:
                            String[] yes = {"true"};
                            outputArray = yes;
                            break;
                        case normal:
                            String[] answer = {"Porovnáno:", "NAG", "NAG", "Obě gramatiky jsou bez nedosažitelných symbolů a jsou stejné."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    teacherTransformed = transform.removeUnreachableSymbols(teacherCFG);
                    if (form.hasUnreachableSymbols(studentCFG)) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nedosažitelné symboly."};
                                outputArray = answer;
                                break;
                        }
                    } else {
                        if (!studentCFG.equals(teacherTransformed)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", form.isNAG(studentCFG) ? "NAG" : studentCFG.toString(), form.isNAG(teacherCFG) ? "NAG" : teacherTransformed.toString(), "Obě gramatiky jsou bez nedosažitelných symbolů, ale nejsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez nedosažitelných symbolů a jsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    }
                }
                break;
            case RED:
                if (form.isNAG(studentCFG) && (form.isNAG(teacherCFG) || !form.languageIsNotEmpty(teacherCFG))) {
                    switch (mode) {
                        case simple:
                            String[] yes = {"true"};
                            outputArray = yes;
                            break;
                        case normal:
                            String[] answer = {"Porovnáno:", "NAG", "NAG", "Obě gramatiky jsou redukované a jsou stejné."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    if (form.hasUnusefullSymbols(studentCFG)) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                if (!form.languageIsNotEmpty(teacherCFG)) {
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), "NAG", "Studentova gramatika stále obsahuje nenormované symboly."};
                                    outputArray = answer;
                                } else {
                                    try {
                                        teacherCFG = transform.makeReducedCFG(teacherCFG);
                                    } catch (TransformationException ex) {
                                        Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nenormované symboly."};
                                    outputArray = answer;
                                }
                                break;
                        }
                    } else if (form.hasUnreachableSymbols(studentCFG)) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                if (!form.languageIsNotEmpty(teacherCFG)) {
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), "NAG", "Studentova gramatika stále obsahuje nedosažitelné symboly."};
                                    outputArray = answer;
                                } else {
                                    try {
                                        teacherTransformed = transform.makeReducedCFG(teacherCFG);
                                    } catch (TransformationException ex) {
                                        Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nedosažitelné symboly."};
                                    outputArray = answer;
                                }
                                break;
                        }
                    } else {
                        if (!form.languageIsNotEmpty(teacherCFG)) {
                            teacherTransformed = new ContextFreeGrammar(new HashSet<String>(), new HashMap<String, Set<String>>(), "");
                        } else {
                            try {
                                teacherTransformed = transform.makeReducedCFG(teacherCFG);
                            } catch (TransformationException ex) {
                                Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (!studentCFG.equals(teacherTransformed)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou redukované, ale nejsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou redukované a jsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    }
                }
                break;
            case EPS:
                if (form.isNAG(studentCFG) && form.isNAG(teacherCFG)) {
                    switch (mode) {
                        case simple:
                            String[] yes = {"true"};
                            outputArray = yes;
                            break;
                        case normal:
                            String[] answer = {"Porovnáno:", "NAG", "NAG", "Obě gramatiky jsou stejné."};
                            outputArray = answer;
                            break;
                    }
                } else if (form.isNAG(studentCFG) || form.isNAG(teacherCFG)) {
                    switch (mode) {
                        case simple:
                            String[] no = {"false"};
                            outputArray = no;
                            break;
                        case normal:
                            String[] answer = {"Neporovnáno:", form.isNAG(studentCFG) ? "NAG" : studentCFG.toString(), form.isNAG(teacherCFG) ? "NAG" : teacherCFG.toString(), "Nesmyslné zadání."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    if (form.hasEpsilonRules(teacherCFG)) {
                        teacherTransformed = transform.removeEps(teacherCFG);
                    } else {
                        teacherTransformed = teacherCFG;
                    }
//                if (form.hasEpsilonRules(studentCFG)) {
//                    switch (mode) {
//                        case simple:
//                            String[] no = {"false"};
//                            outputArray = no;
//                            break;
//                        case normal:
//                            String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje epsilon kroky."};
//                            outputArray = answer;
//                            break;
//                    }
//                } else {
                    if (!studentCFG.equals(teacherTransformed)) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez epsilon kroků, ale nejsou stejné."};
                                outputArray = answer;
                                break;
                        }
                    } else {
                        switch (mode) {
                            case simple:
                                String[] yes = {"true"};
                                outputArray = yes;
                                break;
                            case normal:
                                String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez epsilon kroků a jsou stejné."};
                                outputArray = answer;
                                break;
                        }
                    }
                }
//                }
                break;
            case SRF:
                if (form.isNAG(studentCFG) && form.isNAG(teacherCFG)) {
                    switch (mode) {
                        case simple:
                            String[] yes = {"true"};
                            outputArray = yes;
                            break;
                        case normal:
                            String[] answer = {"Porovnáno:", "NAG", "NAG", "Obě gramatiky jsou stejné."};
                            outputArray = answer;
                            break;
                    }
                } else if (form.isNAG(studentCFG) || form.isNAG(teacherCFG)) {
                    switch (mode) {
                        case simple:
                            String[] no = {"false"};
                            outputArray = no;
                            break;
                        case normal:
                            String[] answer = {"Neporovnáno:", form.isNAG(studentCFG) ? "NAG" : studentCFG.toString(), form.isNAG(teacherCFG) ? "NAG" : teacherCFG.toString(), "Nesmyslné zadání."};
                            outputArray = answer;
                            break;
                    }
                } else {
//                if ((form.hasEpsilonRules(teacherCFG) && form.hasSimpleRules(teacherCFG)) ||
//                        (form.hasEpsilonRules(teacherCFG) && !form.hasSimpleRules(teacherCFG)) ||
//                        (!form.hasEpsilonRules(teacherCFG) && form.hasSimpleRules(teacherCFG))) {
                    teacherTransformed = transform.removeSimpleRules(teacherCFG);
//                } else {
//                    teacherTransformed = teacherCFG;
//                }
//                if (form.hasEpsilonRules(studentCFG)) {
//                    switch (mode) {
//                        case simple:
//                            String[] no = {"false"};
//                            outputArray = no;
//                            break;
//                        case normal:
//                            String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje epsilon kroky."};
//                            outputArray = answer;
//                            break;
//                    }
//                } else
                    if (form.hasSimpleRules(studentCFG)) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje jednoduchá pravidla."};
                                outputArray = answer;
                                break;
                        }
                    } else if (!studentCFG.equals(teacherTransformed)) {
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez jednoduchých pravidel, ale nejsou stejné."};
                                outputArray = answer;
                                break;
                        }
                    } else {
                        switch (mode) {
                            case simple:
                                String[] yes = {"true"};
                                outputArray = yes;
                                break;
                            case normal:
                                String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou bez jednoduchých pravidel a jsou stejné."};
                                outputArray = answer;
                                break;
                        }
                    }
                }
                break;
            case PRO:
                if (form.isNAG(studentCFG) && (form.isNAG(teacherCFG) || !form.languageIsNotEmpty(teacherCFG))) {
                    switch (mode) {
                        case simple:
                            String[] yes = {"true"};
                            outputArray = yes;
                            break;
                        case normal:
                            String[] answer = {"Porovnáno:", "NAG", "NAG", "Obě gramatiky jsou stejné."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    if (!form.languageIsNotEmpty(teacherCFG)) {
                        if (!form.languageIsNotEmpty(studentCFG) && studentCFG.getInitialNonTerminal().equals("")) {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), "NAG", "Gramatiky jsou stejné a vlastní."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), "NAG", "Studentova gramatika generuje neprázdný jazyk, narozdíl od gramatiky učitele. Mělo tedy být zadáno NAG (Not A Grammar)."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    } else {
                        if (!form.languageIsNotEmpty(studentCFG) && studentCFG.getInitialNonTerminal().equals("")) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    try {
                                        teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                                    } catch (TransformationException ex) {
                                        Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika generuje prázdný jazyk, narozdíl od gramatiky učitele."};
                                    outputArray = answer;
                                    break;
                            }
                        } else { //obě generují neprázdný jazyk
//                        if ((form.hasEpsilonRules(teacherCFG) && form.hasSimpleRules(teacherCFG)) ||
//                                (form.hasEpsilonRules(teacherCFG) && !form.hasSimpleRules(teacherCFG)) ||
//                                (!form.hasEpsilonRules(teacherCFG) && form.hasSimpleRules(teacherCFG))) {
                            try {
                                teacherTransformed = transform.makeProperCFG(teacherCFG);
                            } catch (TransformationException ex) {
                                Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                            }
//                        } else {
//                            try {
//                                teacherTransformed = transform.makeReducedCFG(teacherCFG);
//                            } catch (TransformationException ex) {
//                                Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }

//                        if (form.hasEpsilonRules(studentCFG)) {
//                            switch (mode) {
//                                case simple:
//                                    String[] no = {"false"};
//                                    outputArray = no;
//                                    break;
//                                case normal:
//                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje epsilon kroky."};
//                                    outputArray = answer;
//                                    break;
//                            }
//                        } else
                            if (form.hasSimpleRules(studentCFG)) {
                                switch (mode) {
                                    case simple:
                                        String[] no = {"false"};
                                        outputArray = no;
                                        break;
                                    case normal:
                                        String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje jednoduchá pravidla."};
                                        outputArray = answer;
                                        break;
                                }
                            } else if (!form.isReduced(studentCFG)) {
                                switch (mode) {
                                    case simple:
                                        String[] no = {"false"};
                                        outputArray = no;
                                        break;
                                    case normal:
                                        String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nepoužitelné symboly."};
                                        outputArray = answer;
                                        break;
                                }
                            } else if (!studentCFG.equals(teacherTransformed)) {
                                switch (mode) {
                                    case simple:
                                        String[] no = {"false"};
                                        outputArray = no;
                                        break;
                                    case normal:
                                        String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou vlastní, ale nejsou stejné."};
                                        outputArray = answer;
                                        break;
                                }
                            } else {
                                switch (mode) {
                                    case simple:
                                        String[] yes = {"true"};
                                        outputArray = yes;
                                        break;
                                    case normal:
                                        String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou vlastní a jsou stejné."};
                                        outputArray = answer;
                                        break;
                                }
                            }
                        }
                    }
                }
                break;
            case CNF:
                if (form.isNAG(studentCFG) || form.isNAG(teacherCFG)) {
                    switch (mode) {
                        case simple:
                            String[] no = {"false"};
                            outputArray = no;
                            break;
                        case normal:
                            String[] answer = {"Neporovnáno:", form.isNAG(studentCFG) ? "NAG" : studentCFG.toString(), form.isNAG(teacherCFG) ? "NAG" : teacherCFG.toString(), "Nesmyslné zadání."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    if (!form.languageIsNotEmpty(teacherCFG)) {
                        try {
                            teacherTransformed = transform.transformToCNF(teacherCFG);
                        } catch (TransformationException ex) {
                            Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (form.languageIsNotEmpty(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné, protože studentova gramatika generuje neprázdný jazyk narozdíl od gramatiky učitele, generující jazyk prázdný."};
                                    outputArray = answer;
                                    break;
                            }
                        } else if (!studentCFG.equals(teacherTransformed)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné. Pokud jste zadali gramatiku v CNF generující neprázdný jazyk, zadejte příště S->SS, jinak dojde k chybnému vyhodnocení."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou v CNF a jsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    } else if (!form.languageIsNotEmpty(studentCFG)) { //učitelova generuje neprázdný jazyk
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherCFG.toString(), "Studentova gramatika generuje prázdný jazyk, narozdíl od gramatiky učitele generující jazyk neprázdný."};
                                outputArray = answer;
                                break;
                        }
                    } else { //ani jedna negeneruje prázdný jazyk
                        if (form.isInCNF(teacherCFG)) {
                            teacherTransformed = teacherCFG;
                        } else {
                            try {
                                teacherTransformed = transform.transformToCNF(teacherCFG);
                            } catch (TransformationException ex) {
                                Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

//                    if (form.hasEpsilonRules(studentCFG)) {
//                        switch (mode) {
//                            case simple:
//                                String[] no = {"false"};
//                                outputArray = no;
//                                break;
//                            case normal:
//                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje epsilon kroky."};
//                                outputArray = answer;
//                                break;
//                        }
//                    } else
                        if (form.hasSimpleRules(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje jednoduchá pravidla."};
                                    outputArray = answer;
                                    break;
                            }
                        } else if (!form.isReduced(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nepoužitelné symboly."};
                                    outputArray = answer;
                                    break;
                            }
                        } else if (!form.isInCNF(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika není v CNF."};
                                    outputArray = answer;
                                    break;
                            }
                        } else if (!studentCFG.equals(teacherTransformed)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou v CNF a jsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    }
                }
                break;
            case RLR:
                if (form.isNAG(studentCFG) && (form.isNAG(teacherCFG) || !form.languageIsNotEmpty(teacherCFG))) {
                    switch (mode) {
                        case simple:
                            String[] yes = {"true"};
                            outputArray = yes;
                            break;
                        case normal:
                            String[] answer = {"Porovnáno:", "NAG", "NAG", "Obě gramatiky jsou stejné."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    if (!form.languageIsNotEmpty(teacherCFG)) {
                        if (!form.languageIsNotEmpty(studentCFG) && studentCFG.getInitialNonTerminal().equals("")) {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), "NAG", "Gramatiky jsou stejné a bez levé rekurze."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), "NAG", "Studentova gramatika generuje neprázdný jazyk, narozdíl od gramatiky učitele. Mělo tedy být zadáno NAG (Not A Grammar)."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    } else {
                        if (!form.languageIsNotEmpty(studentCFG) && studentCFG.getInitialNonTerminal().equals("")) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    try {
                                        teacherTransformed = transform.removeUnusefullSymbols(teacherCFG);
                                    } catch (TransformationException ex) {
                                        Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika generuje prázdný jazyk, narozdíl od gramatiky učitele."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
//                        if (form.hasEpsilonRules(studentCFG)) {
//                            switch (mode) {
//                                case simple:
//                                    String[] no = {"false"};
//                                    outputArray = no;
//                                    break;
//                                case normal:
//                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje epsilon kroky."};
//                                    outputArray = answer;
//                                    break;
//                            }
//                        } else if (form.hasSimpleRules(studentCFG)) {
//                            switch (mode) {
//                                case simple:
//                                    String[] no = {"false"};
//                                    outputArray = no;
//                                    break;
//                                case normal:
//                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje jednoduchá pravidla."};
//                                    outputArray = answer;
//                                    break;
//                            }
//                        } else
//                            if (!form.isReduced(studentCFG)) {
//                                switch (mode) {
//                                    case simple:
//                                        String[] no = {"false"};
//                                        outputArray = no;
//                                        break;
//                                    case normal:
//                                        String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nepoužitelné symboly."};
//                                        outputArray = answer;
//                                        break;
//                                }
//                        } else if (!form.isLeftRecursive(studentCFG, ordering)) {
//                            switch (mode) {
//                                case simple:
//                                    String[] no = {"false"};
//                                    outputArray = no;
//                                    break;
//                                case normal:
//                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika je levorekurzivní."};
//                                    outputArray = answer;
//                                    break;
//                            }
//                            } else {
//                                Map<ContextFreeGrammar, List[]> returnMap = null;
//                                try {
//                                    returnMap = transform.removeLeftRecursion(teacherCFG, ordering);
//                                } catch (TransformationException ex) {
//                                    Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                for (Map.Entry<ContextFreeGrammar, List[]> entry : returnMap.entrySet()) {
//                                    teacherTransformed = entry.getKey();
//                                }
                                Map<ContextFreeGrammar, List[]> returnMap = null;
                                try {
                                    returnMap = transform.removeLeftRecursion(teacherCFG, ordering);
                                } catch (TransformationException ex) {
                                    Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for (Map.Entry<ContextFreeGrammar, List[]> entry : returnMap.entrySet()) {
                                    teacherTransformed = entry.getKey();
                                }
                                if (!studentCFG.equals(teacherTransformed)) {
                                    switch (mode) {
                                        case simple:
                                            String[] no = {"false"};
                                            outputArray = no;
                                            break;
                                        case normal:
                                            String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné. Pokud jste si jisti, že máte převod správně, uvádějte příště pravidla modelu učitele v tom pořadí v jakém očekáváte následné uspořádání neterminálů."};
                                            outputArray = answer;
                                            break;
                                    }
                                } else {
                                    switch (mode) {
                                        case simple:
                                            String[] yes = {"true"};
                                            outputArray = yes;
                                            break;
                                        case normal:
                                            String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou v bez levé rekurze a jsou stejné."};
                                            outputArray = answer;
                                            break;
                                    }
                                }
//                            }
                        }
                    }
                }
                break;
            case GNF:
                if (form.isNAG(studentCFG) || form.isNAG(teacherCFG)) {
                    switch (mode) {
                        case simple:
                            String[] no = {"false"};
                            outputArray = no;
                            break;
                        case normal:
                            String[] answer = {"Neporovnáno:", form.isNAG(studentCFG) ? "NAG" : studentCFG.toString(), form.isNAG(teacherCFG) ? "NAG" : teacherCFG.toString(), "Nesmyslné zadání."};
                            outputArray = answer;
                            break;
                    }
                } else {
                    if (!form.languageIsNotEmpty(teacherCFG)) {
                        try {
                            teacherTransformed = transform.transformToGNF(teacherCFG, ordering);
                        } catch (TransformationException ex) {
                            Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (form.languageIsNotEmpty(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné, protože studentova gramatika generuje neprázdný jazyk narozdíl od gramatiky učitele, generující jazyk prázdný."};
                                    outputArray = answer;
                                    break;
                            }
                        } else if (!studentCFG.equals(teacherTransformed)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné. Pokud jste zadali gramatiku v GNF generující neprázdný jazyk, zadejte příště S->aS, jinak dojde k chybnému vyhodnocení."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou v GNF a jsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    } else if (!form.languageIsNotEmpty(studentCFG)) { //učitelova generuje neprázdný jazyk
                        switch (mode) {
                            case simple:
                                String[] no = {"false"};
                                outputArray = no;
                                break;
                            case normal:
                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherCFG.toString(), "Studentova gramatika generuje prázdný jazyk, narozdíl od gramatiky učitele generující jazyk neprázdný."};
                                outputArray = answer;
                                break;
                        }
                    } else { //ani jedna negeneruje prázdný jazyk
                        if (form.isInGNF(teacherCFG)) {
                            teacherTransformed = teacherCFG;
                        } else {
                            try {
                                teacherTransformed = transform.transformToGNF(teacherCFG, ordering);
                            } catch (TransformationException ex) {
                                Logger.getLogger(CFGComparator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

//                    if (form.hasEpsilonRules(studentCFG)) {
//                        switch (mode) {
//                            case simple:
//                                String[] no = {"false"};
//                                outputArray = no;
//                                break;
//                            case normal:
//                                String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje epsilon kroky."};
//                                outputArray = answer;
//                                break;
//                        }
//                    } else
                        if (form.hasSimpleRules(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje jednoduchá pravidla."};
                                    outputArray = answer;
                                    break;
                            }
                        } else if (form.hasUnusefullSymbols(studentCFG)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika stále obsahuje nepoužitelné symboly."};
                                    outputArray = answer;
                                    break;
                            }
//                        } else if (!form.isInGNF(studentCFG)) {
//                            switch (mode) {
//                                case simple:
//                                    String[] no = {"false"};
//                                    outputArray = no;
//                                    break;
//                                case normal:
//                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Studentova gramatika není v GNF."};
//                                    outputArray = answer;
//                                    break;
//                            }
                        } else if (!studentCFG.equals(teacherTransformed)) {
                            switch (mode) {
                                case simple:
                                    String[] no = {"false"};
                                    outputArray = no;
                                    break;
                                case normal:
                                    String[] answer = {"Neporovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Gramatiky nejsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        } else {
                            switch (mode) {
                                case simple:
                                    String[] yes = {"true"};
                                    outputArray = yes;
                                    break;
                                case normal:
                                    String[] answer = {"Porovnáno:", studentCFG.toString(), teacherTransformed.toString(), "Obě gramatiky jsou v GNF a jsou stejné."};
                                    outputArray = answer;
                                    break;
                            }
                        }
                    }
                }
                break;
        }
        return outputArray;
    }
}
