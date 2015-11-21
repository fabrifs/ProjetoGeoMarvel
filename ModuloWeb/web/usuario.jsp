
<%@page import="shared.entities.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" 
            content="text/html; charset=UTF-8">
        <title>Usuario</title>
    </head>
    <body>
        <h1>Usuario</h1>
        <% 
            Usuario usuario = (Usuario) request.getAttribute("usuario");
         %>
         
         <b>Nome: <%=usuario.getNome()%></b><br>
         Sobrenome: <%=usuario.getSobrenome()%><br>
         Login: <%=usuario.getLogin()%><br>
         Nova Senha: <%=usuario.getSenha()%><br>
         
         </br>
         
         
            <a href="sucessoLogin.jsp">Voltar para a Home</a>
            
    </body>
</html>