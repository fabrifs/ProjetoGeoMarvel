
package ejb.beans;

import ejb.interceptors.LogInterceptor;
import shared.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
@LocalBean
@Interceptors(LogInterceptor.class)
public class UsuarioBean {
    
 @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;
 
     
    public void save(Usuario u) {
        em.persist(u);
    }
 
    public List<Usuario> list() {
        Query query = em.createQuery("FROM Usuario u");
 
        List<Usuario> list = query.getResultList();
        return list;
    }
    
    public boolean autentica(String user, String passHash){
        Query query = em.createQuery("FROM Usuario u where u.login='" +user + "'");
        List<Usuario> list = query.getResultList();
        if (list.size()!=1){
            return false;
        }
        Usuario u = list.get(0);
        if (user.equals(u.getLogin()) && passHash.equals(u.getSenha()) ){
            return true;
        }
        return false;
    }
    
    public Usuario alteraSenha(String usuario,String senha,String senhaNova){
        Query query = em.createQuery("FROM Usuario u where u.login='" +usuario + "'");
        List<Usuario> list = query.getResultList();
        if (list.size()!=1){
            return null;
        }
        Usuario u = list.get(0);
        if (usuario.equals(u.getLogin()) && senha.equals(u.getSenha()) ){
            u.setSenha(senhaNova);
            em.persist(u);
            return u;
        }
        return null;
        
    }
}
