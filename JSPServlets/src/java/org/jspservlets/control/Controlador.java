/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jspservlets.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jspservlets.objetos.Login;

/**
 *
 * @author marcelo
 */
public class Controlador extends HttpServlet {
   private String seleccion = new String();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        seleccion = request.getParameter("seleccion");
        try {
            if(seleccion.equals("1")){
                Login lg = new Login();
                lg.setName(request.getParameter("user"));
                lg.setPass(request.getParameter("pass"));
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Página Principal</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h3>Bienvenido, usuario " + lg.getName() + "</h3>");
                out.println("<div> su clave es: " + lg.getPass() + ", pero no se preocupe, no se la diremos a nadie</div>");
                out.println("<input type='submit' value='volver' name='volver' onclick='history.go(-1);' />");
                out.println("</body>");
                out.println("</html>");
            }
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controlador at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
