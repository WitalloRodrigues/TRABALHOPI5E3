package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Paciente;

public class PacienteDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public PacienteDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public void salvar(Paciente paciente) {
		try {
            entityManager.getTransaction().begin();
            entityManager.persist(paciente);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void atualizar(Paciente paciente) {
		try {
            entityManager.getTransaction().begin();
            entityManager.merge(paciente);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void excluir(Paciente paciente) {
        try {
            entityManager.getTransaction().begin();
            paciente = entityManager.merge(paciente);
            entityManager.remove(paciente);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
	
	public List<Paciente> listar() {
        try {
            TypedQuery<Paciente> query = entityManager.createQuery("SELECT l FROM Paciente l order by 1 desc", Paciente.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
	
	public Paciente buscarPorId(Long id) {
        return entityManager.find(Paciente.class, id);
    }

}
