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

/**
 *
 * @author Fabrizzio
 */
public class CadastraController extends AbstractController {

    public void execute() {
        try {
            String nome = this.getRequest().getParameter("nome");
            String sobrenome = this.getRequest().getParameter("sobrenome");
            String login = this.getRequest().getParameter("login");
            String senha = this.getRequest().getParameter("senha");
            String senhaConf = this.getRequest().getParameter("senhaConf");
            String avatar = this.getRequest().getParameter("avatar");
            if (senhaConf.equals(senha)) {
                Usuario u = new Usuario();
                u.setNome(nome);
                u.setSobrenome(sobrenome);
                u.setLogin(login);
                u.setSenha(senha);
                u.setAvatar(avatar);
                UsuarioBean ub = (UsuarioBean) InitialContext.doLookup("java:/global/AppCorporativa/ModuloEJB/UsuarioBean");
                ub.save(u);
                this.setReturnPage("/login.html");
            }else{
                throw new Exception("Erro no cadastro");
            }
        } catch (Exception ex) {
            Logger.getLogger(ListaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
