package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entidades.Imc;
import entidades.Paciente;


public class ImcDao {
	
	private EntityManager entityManager;
	

    public ImcDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public void salvar(Imc imc) {
		try {
            entityManager.getTransaction().begin();
            entityManager.persist(imc);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void atualizar(Imc imc) {
		try {
            entityManager.getTransaction().begin();
            entityManager.merge(imc);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void excluir(Imc imc) {
        try {
            entityManager.getTransaction().begin();
            imc = entityManager.merge(imc);
            entityManager.remove(imc);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
	

	
	public List<Imc> listar() {
        TypedQuery<Imc> query = entityManager.createQuery("SELECT a FROM Imc a JOIN FETCH a.paciente",Imc.class);
//        query.setParameter("paciente", paciente.getId());

        return query.getResultList();
    }
	
	
	public int contarImcmentos() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Imc a", Long.class);
        Long resultado = query.getSingleResult();
        return resultado != null ? resultado.intValue() : 0;
    }


}
