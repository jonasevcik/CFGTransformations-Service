/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fja.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.fja.deposit.DepositorException;
import cz.muni.fi.fja.deposit.Task;

public class Export extends HttpServlet {

  private static final long serialVersionUID = -8963392562609568018L;

  protected void processRequest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    boolean tasksFromWeb = false;
    {
      String s = request.getParameter("task");
      if (s != null && s.equals("web")) {
        tasksFromWeb = true;
      }
    }
    response.setContentType("text");
    PrintWriter out = response.getWriter();
    try {
      Collection<Task> lt;
      if (tasksFromWeb) {
        lt = Equal.getDepositorTasksFromWebInterface().getListOfElements();
      } else {
        lt = Equal.getDepositorTasksFromIS().getListOfElements();
      }
      for (Task t : lt) {
        out.print(t.getTInfo());
        out.print("-");
        out.print(t.getSInfo());
        out.print(":");
        out.print(t.getAnswer());
        out.print("\n");
        String teacher = t.getTeacher();
        String student = t.getStudent();
        out.println(teacher.length());
        out.println(student.length());
        out.println(teacher);
        out.println(student);
      }
    } catch (DepositorException de) {
    } finally {
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed"
  // desc="HttpServlet methods. Click on the + sign on the left to edit the code."
  // >
  /**
   * Handles the HTTP <code>GET</code> method.
   * 
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   * 
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   */
  public String getServletInfo() {
    return "Short description";
  }
  // </editor-fold>
}
