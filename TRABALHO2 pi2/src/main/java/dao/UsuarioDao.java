package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import entidades.Usuario;

public class UsuarioDao {
	
	private EntityManager entityManager;
	

    public UsuarioDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public void salvar(Usuario usuario) {
		try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void excluir(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            usuario = entityManager.merge(usuario);
            entityManager.remove(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
	
	public List<Usuario> listar() {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery("SELECT l FROM Usuario l where perfil_id != 0 order by id", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
	
	public Usuario buscarPorLoginSenha(String login, String senha) {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery(
                    "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", Usuario.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Se nenhum usuário for encontrado com o login e senha fornecidos
        }
    }
	
	public Usuario buscarPorLoginEDataNascimento(String login, Date dataNascimento) {
        
		try {TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login AND u.dataNascimento = :dataNascimento",
                Usuario.class);
        query.setParameter("login", login);
        query.setParameter("dataNascimento", dataNascimento);
        return query.getSingleResult();
	} catch (NoResultException e) {
        return null; // Se nenhum usuário for encontrado com o login e senha fornecidos
    }
    }
	
	public Usuario buscarPorId(int id) {
        return entityManager.find(Usuario.class, id);
    }
	
	
	
	
//	public List<Usuario> buscarPorDataHoraEMedico(Integer id, Date dataHoraConsulta, String medico) {
//        TypedQuery<Usuario> query = entityManager.createQuery(
//                "SELECT a FROM Usuario a WHERE a.id != :id AND a.dataHoraConsulta = :dataHoraConsulta AND a.medico = :medico",
//                Usuario.class);
//        query.setParameter("id", id = (id == null) ? 0 : id);
//        query.setParameter("dataHoraConsulta", dataHoraConsulta);
//        query.setParameter("medico", medico);
//        System.out.println("SELECT a FROM Usuario a WHERE a.id !="+id+" AND a.dataHoraConsulta = "+dataHoraConsulta+""
//        		+ "AND a.medico = "+medico);
//        return query.getResultList();
//    }
	
	public int contarUsuariomentos() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Usuario a", Long.class);
        Long resultado = query.getSingleResult();
        return resultado != null ? resultado.intValue() : 0;
    }


}
