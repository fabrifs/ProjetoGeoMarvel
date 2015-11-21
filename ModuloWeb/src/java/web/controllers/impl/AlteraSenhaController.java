/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.impl;

import ejb.beans.UsuarioBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import shared.entities.Usuario;
import web.controllers.AbstractController;


public class AlteraSenhaController extends AbstractController {

    public void execute() {
        try {
            String senha = (String) this.getRequest().getParameter("senha");
            String senhaNova = (String) this.getRequest().getParameter("senhaNova");
            String senhaNovaConfirmacao = (String) this.getRequest().getParameter("senhaNovaConfirmacao");
            String usuario = (String) this.getRequest().getParameter("usuario");
        
            if (senhaNova.equals(senhaNovaConfirmacao)){
       
                UsuarioBean ub = (UsuarioBean) InitialContext.doLookup("java:/global/AppCorporativa/ModuloEJB/UsuarioBean");
                Usuario u = ub.alteraSenha(usuario,senha,senhaNova);
                if (u!=null){
                    this.setReturnPage("/usuario.jsp");
                    this.getRequest().setAttribute("usuario", u);
                } else {
                    this.setReturnPage("/erroAlteraSenha.html");
                }
            } else {
                this.setReturnPage("/erroAlteraSenha.html");
            }
            
          
            
            

        }catch(Exception ex)   { 
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
