<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login bem sucedido</title>
    </head>
    <body>
        <%
        //allow access only if session exists
            String user = (String) session.getAttribute("usuario");
            String userName = null;
            String sessionID = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("usuario")) {
                        userName = cookie.getValue();
                    }
                    if (cookie.getName().equals("JSESSIONID")) {
                        sessionID = cookie.getValue();
                    }
                }
            }
        %>
        <b><h1>
        Bem vindo <%=user%> ! O que voce quer fazer?
        </b></h1>
        <br>
        <a href="FrontControllerServlet?control=Lista">Listar todos os usuarios</a><br></br>
        <form action="ListaNomeServlet?control=Nome" method="post">
            <input type="text" name="nome" required/><br/></br>
            
            <input type="submit" value="Buscar" >
        </form>
        
        </br>
        <a href="alteraSenha.html">Altera senha</a>
        </br>
        </br>
        <a href="http://localhost:8080/AppMonitor-war/SVGViewServlet">Relatório de Uso</a>
        </br>
        </br>
        <a href="mapa.html">Mapa de Posições</a>
        </br>
        </br>
        
        
        
        <br>
        <form action="LogoutServlet" method="post">
            <input type="submit" value="Logout" >
        </form>
        
        
        
    </body>
</html>
