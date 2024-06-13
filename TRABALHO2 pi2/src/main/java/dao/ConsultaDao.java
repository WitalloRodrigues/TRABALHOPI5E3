package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Agendamento;
import entidades.Consulta;
import entidades.Medico;

public class ConsultaDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public ConsultaDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public void salvar(Consulta consulta) {
		try {
            entityManager.getTransaction().begin();
            entityManager.persist(consulta);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void excluir(Consulta consulta) {
        try {
            entityManager.getTransaction().begin();
            consulta = entityManager.merge(consulta);
            entityManager.remove(consulta);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
	
	public List<Consulta> listar() {
        try {
            TypedQuery<Consulta> query = entityManager.createQuery("SELECT l FROM Consulta l", Consulta.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
	
	
	
	public Consulta buscarPorIdDoAgendamento(Long id) {
		TypedQuery<Consulta> query = entityManager.createQuery(
                "SELECT c FROM Consulta c WHERE c.agendamento.id = :id", 
                Consulta.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
