
package web.controllers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.controllers.AbstractController;
import shared.entities.Usuario;


public class IndexController extends AbstractController {

    public void execute() {
        try {
            List usuarios = new ArrayList<Usuario>();
            usuarios.add(new Usuario(1,"Rafael","Nadal","rnadRafaelal","senha", ""));
            usuarios.add(new Usuario(2, "Novak","Djokovic","ndjokovic","senha",""));
            this.setReturnPage("/index.jsp");
            this.getRequest().setAttribute("usuarios", usuarios);

        }catch(Exception ex)   { 
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
