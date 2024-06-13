package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import dao.AgendamentoDAO;
import dao.ConsultaDao;
import dao.MedicoDAO;
import dao.PacienteDao;
import entidades.Agendamento;
import entidades.Consulta;
import entidades.Medico;
import entidades.Paciente;
import entidades.Status;
import util.JPAUtil;

@ManagedBean
@ApplicationScoped
public class ConsultaBean {

	private Consulta consulta;
	private Agendamento agendamento;
	
	private ConsultaDao consultaDao;
	private MedicoDAO medicoDao;
	private PacienteDao pacienteDao;
	private AgendamentoDAO agendamentoDao;
	private String voltar = "listaAgendamentos";
	
	private List<Consulta> consultas;
	private List<Medico> medicos;
	private List<Paciente> pacientes;
	private List<Paciente> filteredPacientes;
	private List<SelectItem> statusOptions;
	
	public ConsultaBean(){
		consulta = new Consulta();
		agendamento = new Agendamento();
		consultaDao = new ConsultaDao(JPAUtil.getEntityManager());
		medicoDao = new MedicoDAO(JPAUtil.getEntityManager());
		pacienteDao = new PacienteDao(JPAUtil.getEntityManager());
		agendamentoDao = new AgendamentoDAO(JPAUtil.getEntityManager());
		
		medicos = medicoDao.listar();
		pacientes = pacienteDao.listar();
		
		statusOptions = new ArrayList<>();
		
        statusOptions.add(new SelectItem("", "Todos"));
        for (Paciente paciente : pacientes) {
        	
        	statusOptions.add(new SelectItem(paciente.getNome(),paciente.getNome()));
        }
        atualizarListaConsultas();
	}
	
	public String consultar(Agendamento agendamento) {

    	this.agendamento = agendamento;
    	agendamento.setStatus(Status.CONSULTANDO);
        agendamentoDao.atualizar(agendamento); 
        this.consulta = consultaDao.buscarPorIdDoAgendamento(agendamento.getId());
        if(consulta == null) {
        	consulta = new Consulta();
        	consulta.setAgendamento(agendamento);
        }
        voltar = "listaAgendamentos";
		return "realizaConsulta";
	}

	
	public void salvar() {
		
		System.out.println(consulta);
	   
	    	
	        if (consulta.getId() == null) {
	        	
	            consultaDao.salvar(consulta);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("consulta salvo com sucesso."));
	            
	        } else {
	            consultaDao.salvar(consulta); 
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("consulta editado com sucesso."));
	        }
	        
	        atualizarListaConsultas();
	    
	}
	
	public void excluir(Consulta consulta) {
        try {
        	consultaDao.excluir(consulta);
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("consulta excluido com sucesso."));
        	atualizarListaConsultas();
        } catch (Exception e) {
        }
    }
	
	public void finalizar(Agendamento agendamento) {
		System.out.println(agendamento);
		
	        agendamento.setStatus(Status.FINALIZADO);
	        agendamentoDao.atualizar(agendamento);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                "Agendamento Finalizado com sucesso.", null));
	   
    }
	
	 public void limparCampos() {
		 consulta = new Consulta();
	 }
	 
	
	 
	 public void cancelarConsulta(Consulta consulta) {
		    try {
		        consultaDao.excluir(consulta); 
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		                "Consulta cancelado com sucesso.", null));
		        atualizarListaConsultas();
		    } catch (Exception e) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                "Erro ao cancelar o consulta.", null));
		    }
	}
	 
	 public void atualizarListaConsultas() {
		consultas = consultaDao.listar();
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public ConsultaDao getConsultaDao() {
		return consultaDao;
	}

	public void setConsultaDao(ConsultaDao consultaDao) {
		this.consultaDao = consultaDao;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
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

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
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

	public List<Paciente> getFilteredPacientes() {
		return filteredPacientes;
	}

	public void setFilteredPacientes(List<Paciente> filteredPacientes) {
		this.filteredPacientes = filteredPacientes;
	}

	public List<SelectItem> getStatusOptions() {
		return statusOptions;
	}

	public void setStatusOptions(List<SelectItem> statusOptions) {
		this.statusOptions = statusOptions;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}
	 
	
	 

}
