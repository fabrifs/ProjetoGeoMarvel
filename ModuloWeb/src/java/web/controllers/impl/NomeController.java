
package web.controllers.impl;

import ejb.beans.UsuarioBean;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import web.controllers.AbstractController;

public class NomeController extends AbstractController  {
    
     public void execute() {
        try {
            String nome = this.getRequest().getParameter("nome");
            UsuarioBean ub = (UsuarioBean)InitialContext.doLookup("java:/global/AppCorporativa/ModuloEJB/UsuarioBean");
            Collection usuarios = ub.listNome(nome);
            this.setReturnPage("/listaNome.jsp");
            this.getRequest().setAttribute("usuarios", usuarios);
        }catch(Exception ex)   { 
            Logger.getLogger(ListaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
    
}
