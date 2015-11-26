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
import utils.Hash;

@Stateless
@LocalBean
@Interceptors(LogInterceptor.class)
public class UsuarioBean {

    Hash hash = new Hash();

    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;

    public void save(Usuario u) {
        u.setSenha(hash.toHash(u.getSenha()));
        em.persist(u);
    }

    public List<Usuario> list() {
        Query query = em.createQuery("FROM Usuario u");
        List<Usuario> list = query.getResultList();
        return list;
    }

    public List<Usuario> listNome(String nome) {
        Query query = em.createQuery("FROM Usuario u where u.nome ='" + nome + "'");
        List<Usuario> list = query.getResultList();
        return list;
    }

    public List<Usuario> listLogin(String login) {
        Query query = em.createQuery("FROM Usuario u where u.login ='" + login + "'");
        List<Usuario> list = query.getResultList();
        return list;
    }

    public boolean autentica(String user, String senha) {
        Query query = em.createQuery("FROM Usuario u where u.login='" + user + "'");
        List<Usuario> list = query.getResultList();
        if (list.size() != 1) {
            return false;
        }
        Usuario u = list.get(0);
        if (user.equals(u.getLogin()) && senha.equals(u.getSenha())) {
            return true;
        } else if (user.equals(u.getLogin()) && hash.toHash(senha).equals(u.getSenha())) {
            return true;
        }
        return false;
    }

    public Usuario alteraSenha(String usuario, String senha, String senhaNova) {
        Query query = em.createQuery("FROM Usuario u where u.login='" + usuario + "'");
        List<Usuario> list = query.getResultList();
        if (list.size() != 1) {
            return null;
        }
        Usuario u = list.get(0);
        if (usuario.equals(u.getLogin()) && senha.equals(u.getSenha())) {
            u.setSenha(hash.toHash(senhaNova));
            em.persist(u);
            return u;
        } else if (usuario.equals(u.getLogin()) && hash.toHash(senha).equals(u.getSenha())) {
            u.setSenha(hash.toHash(senhaNova));
            em.persist(u);
            return u;
        }
        return null;

    }

}
