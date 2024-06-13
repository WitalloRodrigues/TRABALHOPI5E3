package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;

import dao.AgendamentoDAO;
import dao.ImcDao;
import dao.MedicoDAO;
import dao.PacienteDao;
import entidades.Agendamento;
import entidades.Imc;
import entidades.Medico;
import entidades.Paciente;
import entidades.Status;
import util.JPAUtil;

@ManagedBean

@ApplicationScoped
public class ImcBean {

	private Imc imc;
	private Agendamento agendamento;
	
	private ImcDao imcDao;
	private MedicoDAO medicoDao;
	private PacienteDao pacienteDao;
	private AgendamentoDAO agendamentoDao;
	private List<ImcClassificacao> classificacoesIMC;
	private String voltar = "listaAgendamentos";
	
	private List<Imc> imcs;
	private List<Medico> medicos;
	private List<Paciente> pacientes;
	private List<Paciente> filteredPacientes;
	private List<SelectItem> statusOptions;
	
	public ImcBean(){
		imc = new Imc();
		agendamento = new Agendamento();
		imcDao = new ImcDao(JPAUtil.getEntityManager());
		medicoDao = new MedicoDAO(JPAUtil.getEntityManager());
		pacienteDao = new PacienteDao(JPAUtil.getEntityManager());
		agendamentoDao = new AgendamentoDAO(JPAUtil.getEntityManager());
		
		medicos = medicoDao.listar();
		pacientes = pacienteDao.listar();
        
     // Inicializar a lista de classificações IMC
        classificacoesIMC = new ArrayList<>();
        classificacoesIMC.add(new ImcClassificacao(1, "Menor que 16,9", "Muito abaixo do peso", false));
        classificacoesIMC.add(new ImcClassificacao(2, "17 a 18,4", "Abaixo do peso", false));
        classificacoesIMC.add(new ImcClassificacao(3, "18,5 a 24,9", "Peso normal", false));
        classificacoesIMC.add(new ImcClassificacao(4, "25 a 29,9", "Acima do peso", false));
        classificacoesIMC.add(new ImcClassificacao(5, "30 a 34,9", "Obesidade grau I", false));
        classificacoesIMC.add(new ImcClassificacao(6, "35 a 40", "Obesidade grau II", false));
        classificacoesIMC.add(new ImcClassificacao(7, "Maior que 40", "Obesidade grau III", false));
        
        statusOptions = new ArrayList<>();
		
        statusOptions.add(new SelectItem("", "Todos"));
        for (Paciente paciente : pacientes) {
        	
        	statusOptions.add(new SelectItem(paciente.getNome(),paciente.getNome()));
        }
        atualizarListaImcs();
	}
	
	public String imc(Agendamento agendamento) {
		if(agendamento.getId() != null) {
			limparCampos();
			this.imc.setAgendamento(agendamento);
			this.imc.setPaciente(agendamento.getPaciente());
	    	this.agendamento = agendamento;
	    	destacarImcClassificacoes(0);
	    	agendamento.setStatus(Status.ATENDENDO);
	        agendamentoDao.atualizar(agendamento); 
		}
		voltar = "listaAgendamentos";
		return "cadastroImc";
	}
	
	public String CadastroImc() {

        limparCampos();
		
		return "cadastroImc";
	}
	
	public void salvar() {
	   
	    	
	    	calcularImc();
	    	
	        if (imc.getId() == null) {
	        	
	            imcDao.salvar(imc);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("imc salvo com sucesso."));
	            
	        } else {
	            imcDao.atualizar(imc); 
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("imc editado com sucesso."));
	        }
	        destaque();
//	        atualizarInterfaceUsuario();
	        atualizarListaImcs();
	        
	    
	}
	
	public void destaque() {
	    double resultado = imc.getResultado();
	    if (resultado < 16.1) {
	        destacarImcClassificacoes(1);
	    } else if (resultado >= 16.1 && resultado < 18.5) {
	        destacarImcClassificacoes(2);
	    } else if (resultado >= 18.5 && resultado < 25) {
	        destacarImcClassificacoes(3);
	    } else if (resultado >= 25 && resultado < 30) {
	        destacarImcClassificacoes(4);
	    } else if (resultado >= 30 && resultado < 35) {
	        destacarImcClassificacoes(5);
	    } else if (resultado >= 35 && resultado < 40) {
	        destacarImcClassificacoes(6);
	    } else if (resultado >= 40) {
	        destacarImcClassificacoes(7);
	    }
	}
	
	

	
	public void destacarImcClassificacoes(int id) {
		System.out.println(id);
	    for (ImcClassificacao classificacao : classificacoesIMC) {
	        if (classificacao.getId() == id) {
	            classificacao.setDestaque(true);
	        }else {
	        	classificacao.setDestaque(false);
	        }
	    }
	    
	}
	
//	public void atualizarInterfaceUsuario() {
//	    PrimeFaces.current().ajax().update("suaTabelaID"); // Substitua "suaTabelaID" pelo ID da sua tabela
//	}


	
	 public void calcularImc() {
	        if (imc.getPeso() != 0 && imc.getAltura() != 0) {
	            double peso = imc.getPeso();
	            double altura = imc.getAltura();
	            double resultado = peso / (altura * altura);
	            imc.setResultado(resultado); 
	        }
	    }
	 
	public void excluir(Imc imc) {
        try {
        	imcDao.excluir(imc);
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("imc excluido com sucesso."));
        	atualizarListaImcs();
        } catch (Exception e) {
        }
    }
	
	public void finalizar(Agendamento agendamento) {
		System.out.println(agendamento);
		
	        agendamento.setStatus(Status.REALIZADO);
	        agendamentoDao.atualizar(agendamento);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                "Agendamento Realizado com sucesso.", null));
	        limparCampos();
	        imc.setPaciente(agendamento.getPaciente());
	        destacarImcClassificacoes(0);
	   
    }
	
	 public void limparCampos() {
		 imc = new Imc();
	 }
	 
	 public String editar(Imc imc) {
		 
		 voltar = "listaImcs";
	    	this.imc = imc;
	    	destaque();
//	        atualizarInterfaceUsuario();
			return "cadastroImc";
		}
	 
	 public void cancelarImc(Imc imc) {
		    try {
		        imcDao.excluir(imc); 
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		                "Imc cancelado com sucesso.", null));
		        atualizarListaImcs();
		    } catch (Exception e) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                "Erro ao cancelar o imc.", null));
		    }
	}
	 
	 public void atualizarListaImcs() {
		 System.out.println("proximo");
		imcs = imcDao.listar();
	}

	public Imc getImc() {
		return imc;
	}

	public void setImc(Imc imc) {
		this.imc = imc;
	}

	public ImcDao getImcDao() {
		return imcDao;
	}

	public void setImcDao(ImcDao imcDao) {
		this.imcDao = imcDao;
	}

	public List<Imc> getImcs() {
		return imcs;
	}

	public void setImcs(List<Imc> imcs) {
		this.imcs = imcs;
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
	
	
	 
	
	public List<ImcClassificacao> getClassificacoesIMC() {
		return classificacoesIMC;
	}

	public void setClassificacoesIMC(List<ImcClassificacao> classificacoesIMC) {
		this.classificacoesIMC = classificacoesIMC;
	}
	
	
	




	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
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






	public static class ImcClassificacao {
		private int id;
        private String faixa;
        private String classificacao;
        private Boolean destaque;

        public ImcClassificacao(int id, String faixa, String classificacao , Boolean destaque) {
        	this.id = id;
        	this.faixa = faixa;
            this.classificacao = classificacao;
            this.destaque = destaque;
        }
        
        

        @Override
		public String toString() {
			return "ImcClassificacao [id=" + id + ", faixa=" + faixa + ", classificacao=" + classificacao
					+ ", destaque=" + destaque + "]";
		}



		public String getFaixa() {
            return faixa;
        }

        public void setFaixa(String faixa) {
            this.faixa = faixa;
        }

        public String getClassificacao() {
            return classificacao;
        }

        public void setClassificacao(String classificacao) {
            this.classificacao = classificacao;
        }

		public Boolean getDestaque() {
			return destaque;
		}

		public void setDestaque(Boolean destaque) {
			this.destaque = destaque;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
        
    }
	
	 

}
