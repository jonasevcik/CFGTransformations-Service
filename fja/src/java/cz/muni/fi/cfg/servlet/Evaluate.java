/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.cfg.servlet;

import cz.muni.fi.cfg.grammar.ContextFreeGrammar;
import cz.muni.fi.cfg.conversions.Modes;
import cz.muni.fi.cfg.conversions.CFGComparator;
import cz.muni.fi.cfg.conversions.TransformationTypes;
import cz.muni.fi.cfg.parser.ParserException;
import cz.muni.fi.cfg.parser.Parser;
import cz.muni.fi.cfg.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author NICKT
 */
public class Evaluate extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String teacherIS = request.getParameter("t");
        String studentIS = request.getParameter("s");

        String studentData = request.getParameter("studentData");
        String teacherData = request.getParameter("teacherData");

        Modes mode = null;
        TransformationTypes stud = null;

        Parser parser = new Parser();
        if (studentIS != null) {
            studentData = studentIS;
            mode = Modes.simple;
            String[] parsedArray = null;
            try {
                parsedArray = parser.parseISString(teacherIS);
            } catch (ParserException ex) {
                PrintWriter out = response.getWriter();
                try {
                    out.println("false");
                } finally {
                    out.close();
                }
            }
            stud = TransformationTypes.valueOf(parsedArray[0]);
            teacherData = parsedArray[1];
        } else {
            try {
                mode = Modes.valueOf(request.getParameter("mode"));
                stud = TransformationTypes.valueOf(request.getParameter("stud"));
            } catch (Exception e) {
                request.setAttribute("error", "Nebyly vyplněny žádné údaje.");
                request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
                return;
            }
            if (studentData.equals("") || studentData == null || teacherData.equals("") || teacherData == null) {
                request.setAttribute("error2", "Alespoň v jednom poli nebyla zadána gramatika.");
                request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
                return;
            }
        }
        //parsování
        ContextFreeGrammar studentCFG = null;
        ContextFreeGrammar teacherCFG = null;
        List<String> ordering = null;
        if (teacherData.equalsIgnoreCase("nag")) {
            teacherCFG = new ContextFreeGrammar(new HashSet<String>(), new HashMap<String, Set<String>>(), "");
        } else {
            try {
                teacherCFG = parser.parse(teacherData);
                ordering = parser.orderingOfNonTerminals(teacherData);
            } catch (ParserException ex) {
                request.setAttribute("error2", "Model učitele: " + ex.getMessage());
                request.setAttribute("studentData", studentData);
                request.setAttribute("teacherData", teacherData);
                request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
                return;
            }
        }
        if (studentData.equalsIgnoreCase("nag")) {
            studentCFG = new ContextFreeGrammar(new HashSet<String>(), new HashMap<String, Set<String>>(), "");
        } else {
            try {
                studentCFG = parser.parse(studentData);
            } catch (ParserException ex) {
                request.setAttribute("error2", "Model studenta: " + ex.getMessage());
                request.setAttribute("studentData", studentData);
                request.setAttribute("teacherData", teacherData);
                request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
                return;
            }
        }
        CFGComparator comparator = new CFGComparator();
        if (mode.equals(Modes.simple)) {
            PrintWriter out = response.getWriter();
            try {
                out.println(comparator.compare(studentCFG, teacherCFG, ordering, stud, mode)[0]);
            } finally {
                out.close();
            }
        } else {
            request.setAttribute("windowData", comparator.compare(studentCFG, teacherCFG, ordering, stud, mode));
            request.setAttribute("studentData", studentData);
            request.setAttribute("teacherData", teacherData);
            request.getRequestDispatcher("/resultevaluation.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
