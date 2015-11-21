package web.controllers.impl;

import ejb.beans.UsuarioBean;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import web.controllers.AbstractController;

public class ListaController extends AbstractController {

    public void execute() {
        try {
            UsuarioBean ub = (UsuarioBean)InitialContext.doLookup("java:/global/AppCorporativa/ModuloEJB/UsuarioBean");
            Collection usuarios = ub.list();
            this.setReturnPage("/lista.jsp");
            this.getRequest().setAttribute("usuarios", usuarios);
        }catch(Exception ex)   { 
            Logger.getLogger(ListaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
