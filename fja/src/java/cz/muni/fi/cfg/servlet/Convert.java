/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.cfg.servlet;

import cz.muni.fi.cfg.grammar.ContextFreeGrammar;

import cz.muni.fi.cfg.conversions.CFGConvertor;
import cz.muni.fi.cfg.conversions.Modes;
import cz.muni.fi.cfg.conversions.TransformationTypes;
import cz.muni.fi.cfg.parser.ParserException;
import cz.muni.fi.cfg.parser.Parser;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author NICKT
 */
public class Convert extends HttpServlet {

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
        String inputData = request.getParameter("inputData");
        boolean generateISString = Boolean.parseBoolean(request.getParameter("generateISString"));
        Modes mode = null;
        TransformationTypes stud = null;
        try {
            mode = Modes.valueOf(request.getParameter("mode"));
            stud = TransformationTypes.valueOf(request.getParameter("stud"));
        } catch (Exception e) {
            request.setAttribute("error", "Nebyly vyplněny žádné údaje.");
            request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
            return;
        }
        if (inputData.equals("") || inputData == null) {
            request.setAttribute("error", "Nebyla zadána žádná gramatika.");
            request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
            return;
        }

        Parser parser = new Parser();
        ContextFreeGrammar cfg = null;
        List<String> ordering = null;
        try {
            cfg = parser.parse(inputData);
            ordering = parser.orderingOfNonTerminals(inputData);
        } catch (ParserException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("inputData", inputData);
            request.getRequestDispatcher("/indexcfg.jsp").forward(request, response);
            return;
        }

        if (generateISString) {
            request.setAttribute("ISString", "b:CFG-" + stud.toString() + ":" + cfg.toString().replaceAll(" ", "").replaceAll("[,]", ", ").replaceAll("\\n", "").replaceAll("\\r", ""));
        }
        CFGConvertor convertor = new CFGConvertor();
        Map<String, String> resultMap = convertor.convert(cfg, stud, ordering, mode);
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            entry.setValue(entry.getValue().replaceAll(",", ""));
        }
        request.setAttribute("windowData", resultMap);
        request.setAttribute("inputData", inputData);
        request.getRequestDispatcher("/resultconversion.jsp").forward(request, response);
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
