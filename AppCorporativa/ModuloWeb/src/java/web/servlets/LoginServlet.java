package web.servlets;

import ejb.beans.UsuarioBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String userID = "admin";
    private final String password = "senha";
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        // get request parameters for userID and password
        String usuarioRequest = request.getParameter("usuario");
        String senhaRequest = request.getParameter("senha");
        
        boolean logged = false;
        try {
            UsuarioBean ub = (UsuarioBean)InitialContext.doLookup("java:/global/AppCorporativa/ModuloEJB/UsuarioBean");
            logged = ub.autentica(usuarioRequest, senhaRequest);
        } catch (NamingException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        if(logged){
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuarioRequest);
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
            Cookie userName = new Cookie("usuario", usuarioRequest);
            userName.setMaxAge(30*60);
            response.addCookie(userName);
            response.sendRedirect("sucessoLogin.jsp");
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Usuario ou senha incorretos.</font>");
            rd.include(request, response);
        }
 
    }
}
