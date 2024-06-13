package bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.AgendamentoDAO;
import dao.MedicoDAO;
import dao.PacienteDao;
import entidades.Agendamento;
import entidades.Imc;
import entidades.Medico;
import entidades.Paciente;
import entidades.Status;
import util.JPAUtil;

@ManagedBean
public class AgendamentoBean {

	private Agendamento agendamento;
	private Imc imc;
	private AgendamentoDAO agendamentoDao;
	private MedicoDAO medicoDao;
	private PacienteDao pacienteDao;
	private String statusFiltro;
	
	private List<Agendamento> agendamentos;
	
	private List<Medico> medicos;
	private List<Paciente> pacientes;
	private List<Agendamento> filteredAgendamentos;
    private List<SelectItem> statusOptions;
	
	public AgendamentoBean(){
		agendamento = new Agendamento();
		imc = new Imc();
		agendamentoDao = new AgendamentoDAO(JPAUtil.getEntityManager());
		medicoDao = new MedicoDAO(JPAUtil.getEntityManager());
		pacienteDao = new PacienteDao(JPAUtil.getEntityManager());
		
		medicos = medicoDao.listar();
		pacientes = pacienteDao.listar();
		
		statusOptions = new ArrayList<>();
		statusOptions.add(new SelectItem("", "Todos"));
        statusOptions.add(new SelectItem("AGENDADO", "AGENDADO"));
        statusOptions.add(new SelectItem("ATENDENDO", "ATENDENDO"));
        statusOptions.add(new SelectItem("REALIZADO", "REALIZADO"));
        statusOptions.add(new SelectItem("CONSULTANDO", "CONSULTANDO"));
        statusOptions.add(new SelectItem("FINALIZADO", "FINALIZADO"));
        
        atualizarListaAgendamentos();
	}
	
	
	public MedicoDAO getMedicoDao() {
		return medicoDao;
	}


	public void setMedicoDao(MedicoDAO medicoDao) {
		this.medicoDao = medicoDao;
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


	public Status[] getStatuses() {
        return Status.values();
    }
	
	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public String editar(Agendamento agendamento) {
    	this.agendamento = agendamento;
    	System.out.println(agendamento);
		return "cadastroAgendamento";
	}
	
	public String voltar() {
		limparCampos();
		atualizarListaAgendamentos();
		return "listaAgendamentos";
	}
	
	public void atualizarListaAgendamentos() {
		agendamentos = agendamentoDao.listar();
		
		
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public AgendamentoDAO getAgendamentoDao() {
		return agendamentoDao;
	}

	public void setAgendamentoDao(AgendamentoDAO agendamentoDao) {
		this.agendamentoDao = agendamentoDao;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}
	
	private boolean agendamentoJaExiste() {
        List<Agendamento> agendamentosExistentes = agendamentoDao.buscarPorDataHoraMedicoClinica(agendamento.getId(), agendamento.getDataHoraAgendamento(), agendamento.getMedico(),agendamento.getClinica());
        return !agendamentosExistentes.isEmpty();
        
        
    }
	
	public void salvar() {
	  
	    	
	        if (agendamento.getId() == null) {
	            agendamentoDao.salvar(agendamento);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("Agendamento salvo com sucesso."));
	            
	        } else {
	            agendamentoDao.atualizar(agendamento); 
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("Agendamento editado com sucesso."));
	        }
	        limparCampos();
	        atualizarListaAgendamentos();
	    
	}
	
	public void excluir(Agendamento agendamento) {
        try {
        	agendamentoDao.excluir(agendamento);
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("agendamento excluido com sucesso."));
        	atualizarListaAgendamentos();
        } catch (Exception e) {
        }
    }
	
	 public void limparCampos() {
		 agendamento = new Agendamento();
	 }
	 public String imc() {
	    	
			return "listaImcs";
		}

	 
	 public String consultar(Agendamento agendamento) {
	    	this.agendamento = agendamento;
			return "realizaConsulta";
		}
	 
	 public void cancelarAgendamento(Agendamento agendamento) {
		    try {
		        agendamento.setStatus(Status.CANCELADO);
		        agendamentoDao.salvar(agendamento); // ou método específico para atualizar apenas o status
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		                "Agendamento cancelado com sucesso.", null));
		        atualizarListaAgendamentos();
		    } catch (Exception e) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                "Erro ao cancelar o agendamento.", null));
		    }
		}
	 
	 public void realizar(Agendamento agendamento) {
		 System.out.println(agendamento);
		    try {
		        agendamento.setStatus(Status.REALIZADO);
		        agendamentoDao.atualizar(agendamento); 
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		                "Imc realizado com sucesso.", null));
		        atualizarListaAgendamentos();
		    } catch (Exception e) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                "Erro ao realzar o Imc.", null));
		    }
		}


	public Imc getImc() {
		return imc;
	}


	public void setImc(Imc imc) {
		this.imc = imc;
	}





	public String getStatusFiltro() {
		return statusFiltro;
	}


	public void setStatusFiltro(String statusFiltro) {
		this.statusFiltro = statusFiltro;
	}


	public List<Agendamento> getFilteredAgendamentos() {
		return filteredAgendamentos;
	}


	public void setFilteredAgendamentos(List<Agendamento> filteredAgendamentos) {
		this.filteredAgendamentos = filteredAgendamentos;
	}


	public List<SelectItem> getStatusOptions() {
		return statusOptions;
	}


	public void setStatusOptions(List<SelectItem> statusOptions) {
		this.statusOptions = statusOptions;
	}
	
	
	

}
