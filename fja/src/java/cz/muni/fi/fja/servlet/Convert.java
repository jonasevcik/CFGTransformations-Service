package cz.muni.fi.fja.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.fja.Centre;

/**
 * This servlet converts entered model to the chosen model.
 * 
 * All possible parameters:
 * "mod" - (=verbose, details, simple). Optional parameter if "mod" is 
 *         empty then default value is "simple".
 *         If "mod" is simple then is returned only "true"/"false".
 * "gen" - (=yes). Optional parameter if "gen" returns "yes" then generate 
 *         teacher's string.
 * "t" - Mandatory parameter. This parameter has to contain teacher's model
 *       and can contain task definition.
 * "teach" - Optional parameter. When the request is comming from web interface
 *           then this parameter can contain type of teacher's model.
 *           Otherwise this should be defined in parameter "t".  
 * "stud" - Optional parameter. When the request is comming from web interface
 *          then this parameter can contain type of requested student's model.
 *          Otherwise this should be defined in parameter "t".
 *           
 * @author Bronek
 */
public class Convert extends HttpServlet {

  /*****************************************************************
   *                                                               *
   *   Public                                                      *
   *                                                               *
   *****************************************************************/

  /**
   * Returns a short description of the servlet.
   */
  public String getServletInfo() {
    return "Convert entered device";
  }
  
  /*****************************************************************
   *                                                               *
   *   Proteceted                                                  *
   *                                                               *
   *****************************************************************/
  
  protected void processRequest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    String mod = request.getParameter("mod");
    boolean generateQuestion = "yes".equals(request.getParameter("gen"));
    boolean verbose = false;
    boolean details = false;
    if (mod != null) {
      if (mod.equals("verbose")) {
        verbose = true;
      } else if (mod.equals("details")) {
        verbose = true;
        details = true;
      }
    }

    String model = request.getParameter("t");
    String modelInfo = request.getParameter("teach");
    String convertInfo = request.getParameter("stud");

    Centre c = new Centre(verbose, details);
    c.convertDevice(modelInfo, model, convertInfo);

    PrintWriter out = response.getWriter();
    try {
      if (!verbose) {
        out.println(c);
      } else {
        response.setContentType("text/html;charset=UTF-8");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>FJAAnswer</title>");
        out.println("<link rel='stylesheet' type='text/css' href='css/equals.css'>");
        out.println("</head>");
        out.println("<body><center>");
        if (generateQuestion) {
          out.println("<table>");
          out.println("<tr><td class=title>Vygenerovany retezec pro odpovednik:</td></tr>");
          out.println("<tr><td class=model>" + c.getQuestion() + "</td></tr>");
          out.println("</table>");
          out.println("<hr>");
        }
        out.println(c);
        out.println("</center></body>");
        out.println("</html>");
      }
    } finally {
      out.close();
    }

  }

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

  /*****************************************************************
   *                                                               *
   *   Private                                                     *
   *                                                               *
   *****************************************************************/

  private static final long serialVersionUID = 8476050747795050901L;

}
