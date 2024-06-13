package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Medico;

public class MedicoDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public MedicoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public void salvar(Medico medico) {
		try {
            entityManager.getTransaction().begin();
            entityManager.persist(medico);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void excluir(Medico medico) {
        try {
            entityManager.getTransaction().begin();
            medico = entityManager.merge(medico);
            entityManager.remove(medico);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
	
	public List<Medico> listar() {
        try {
            TypedQuery<Medico> query = entityManager.createQuery("SELECT l FROM Medico l", Medico.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
	
	public Medico buscarPorId(Long id) {
        return entityManager.find(Medico.class, id);
    }

}
