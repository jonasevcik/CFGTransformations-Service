package cz.muni.fi.fja.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.fja.Centre;
import cz.muni.fi.fja.deposit.Depositor;
import cz.muni.fi.fja.deposit.DepositorException;
import cz.muni.fi.fja.deposit.DepositorListImpl;

/**
 * This servlet analyse tasks from web interface and tasks from IS.
 * 
 * All possible parameters:
 * "mod" - (=verbose, details, simple). Optional parameter if "mod" is 
 *         empty then default value is "simple".
 *         If "mod" is simple then is returned only "true"/"false".
 * "gen" - (=yes). Optional parameter if "gen" returns "yes" then generate 
 *         teacher's string.
 * "t" - Mandatory parameter. This parameter has to contain teacher's model
 *       and can contain task definition.
 * "s" - Mandatory parameter. This parameter has to contain student's model.
 * "teach" - Optional parameter. When the request is comming from web interface
 *           then this parameter can contain type of teacher's model.
 *           Otherwise this should be defined in parameter "t".  
 * "stud" - Optional parameter. When the request is comming from web interface
 *          then this parameter can contain type of requested student's model.
 *          Otherwise this should be defined in parameter "t".
 *           
 * @author Bronek
 */
public class Equal extends HttpServlet {

    /*****************************************************************
     *                                                               *
     *   Public                                                      *
     *                                                               *
     *****************************************************************/
    /**
     * Create depositors (they are singletons).
     *
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() {
        createDepositors();
    }

    /**
     * Return depositor specified to store tasks from IS.
     */
    public static Depositor getDepositorTasksFromIS() {
        if (depositorTasksFromIS == null) {
            createDepositors();
        }
        return depositorTasksFromIS;
    }

    /**
     * Return depositor specified to store tasks from WEB interface.
     */
    public static Depositor getDepositorTasksFromWebInterface() {
        if (depositorTasksFromWebInterface == null) {
            createDepositors();
        }
        return depositorTasksFromWebInterface;
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

    /*****************************************************************
     *                                                               *
     *   Protected                                                   *
     *                                                               *
     *****************************************************************/
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    // </editor-fold>

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("teach") == null && request.getParameter("t").substring(0, 3).equals("CFG")) {
            request.getRequestDispatcher("/evaluatecfg").forward(request, response);
        } else {
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

            String teacher = request.getParameter("t");
            String student = request.getParameter("s");

            String tInfo = request.getParameter("teach");
            String sInfo = request.getParameter("stud");

            Centre c = new Centre(verbose, details);

            boolean answer;
            teacher = teacher == null ? "" : teacher;
            student = student == null ? "" : student;

            if (tInfo == null) {
                answer = c.equalDevices(teacher, student);
            } else {
                answer = c.equalDevices(tInfo, teacher, sInfo, student);
            }

            if (mod != null) {
                try {
                    getDepositorTasksFromWebInterface().insertTask(c.getTeacherTask(), c.getStudentTask(), teacher, student, answer ? "T" : "F");
                } catch (DepositorException de) {
                }
            } else {
                try {
                    getDepositorTasksFromIS().insertTask(c.getTeacherTask(), c.getStudentTask(), teacher, student, answer ? "T" : "F");
                } catch (DepositorException de) {
                }
            }

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
    }

    /*****************************************************************
     *                                                               *
     *   Private                                                     *
     *                                                               *
     *****************************************************************/
    private static void createDepositors() {
        depositorTasksFromIS = new DepositorListImpl();
        depositorTasksFromWebInterface = new DepositorListImpl();
    }
    private static final long serialVersionUID = 4112270898653408724L;
    private static Depositor depositorTasksFromIS;
    private static Depositor depositorTasksFromWebInterface;
}