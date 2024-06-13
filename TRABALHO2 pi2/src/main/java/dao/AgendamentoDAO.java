package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Agendamento;
import entidades.Medico;

public class AgendamentoDAO {

	@PersistenceContext
    private EntityManager entityManager;
	

    public AgendamentoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	
	public void salvar(Agendamento agendamento) {
		try {
            entityManager.getTransaction().begin();
            entityManager.persist(agendamento);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void atualizar(Agendamento agendamento) {
		try {
            entityManager.getTransaction().begin();
            entityManager.merge(agendamento);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
	}
	
	public void excluir(Agendamento agendamento) {
        try {
            entityManager.getTransaction().begin();
            agendamento = entityManager.merge(agendamento);
            entityManager.remove(agendamento);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
	
	public List<Agendamento> listar() {
        try {
            TypedQuery<Agendamento> query = entityManager.createQuery("SELECT a FROM Agendamento a order by a desc", Agendamento.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
	
	public List<Agendamento> listarRealizados() {
        try {
            TypedQuery<Agendamento> query = entityManager.createQuery("SELECT a FROM Agendamento a WHERE a.status = 'REALIZADO'", Agendamento.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }
	
	public List<Agendamento> buscarPorDataHoraMedicoClinica(Long id, Date dataHoraConsulta, Medico medico, String clinica) {
        TypedQuery<Agendamento> query = entityManager.createQuery(
                "SELECT a FROM Agendamento a WHERE a.id != :id AND a.data_hora_agendamento = :dataHoraConsulta AND a.medico_id = :medico AND a.clinica = :clinica AND a.status ='AGENDADO'",
                Agendamento.class);
        query.setParameter("id", id = (id == null) ? 0 : id);
        query.setParameter("dataHoraConsulta", dataHoraConsulta);
        query.setParameter("medico", medico.getId());
        query.setParameter("clinica", clinica);

        return query.getResultList();
    }
	
	
	
	
}
