package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.PacienteDao;
import entidades.Paciente;
import util.JPAUtil;

@ManagedBean
@ApplicationScoped
public class PacienteBean {

	private Paciente paciente;
	private PacienteDao pacienteDao;
	private List<Paciente> pacientes;
	
	public PacienteBean(){
		paciente = new Paciente();
		pacienteDao = new PacienteDao(JPAUtil.getEntityManager());
        atualizarListaPacientes();
	}
	
	public void salvar() {
	    try {
	    	
	        if (paciente.getId() == null) {
	        	
	            pacienteDao.salvar(paciente);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("Paciente salvo com sucesso."));
	            
	        } else {
	            pacienteDao.atualizar(paciente); 
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("Paciente editado com sucesso."));
	        }
	        limparCampos();	
	        atualizarListaPacientes();
	    } catch (Exception e) {
	    	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Erro Grave!!!."));
	    }
	}
	
	public void excluir(Paciente paciente) {
        try {
        	pacienteDao.excluir(paciente);
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("paciente excluido com sucesso."));
        	atualizarListaPacientes();
        } catch (Exception e) {
        }
    }
	
	 public void limparCampos() {
		 paciente = new Paciente();
	 }
	 
	 public String editar(Paciente paciente) {
	    	this.paciente = paciente;
			return "cadastroPaciente";
		}
	 
	 public void cancelarPaciente(Paciente paciente) {
		    try {
		        pacienteDao.excluir(paciente); // ou método específico para atualizar apenas o status
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		                "Paciente cancelado com sucesso.", null));
		        atualizarListaPacientes();
		    } catch (Exception e) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                "Erro ao cancelar o paciente.", null));
		    }
	}
	 
	 public void atualizarListaPacientes() {
		pacientes = pacienteDao.listar();
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public PacienteDao getPacienteDao() {
		return pacienteDao;
	}

	public void setPacienteDao(PacienteDao pacienteDao) {
		this.pacienteDao = pacienteDao;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	 
	 

}
