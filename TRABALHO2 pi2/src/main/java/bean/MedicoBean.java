package bean;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.MedicoDAO;
import entidades.Medico;
import util.JPAUtil;

@ManagedBean
@ApplicationScoped
public class MedicoBean {
	
	private Medico medico;
    private MedicoDAO MedicoDao;
    private List<Medico> medicos;
    
    
    public Medico getMedico() {
		return medico;
	}


	public void setMedico(Medico medico) {
		this.medico = medico;
	}


	public String listagem() {
    	limparCampos();
    	return "medicomentos";
    }
	
    
    public void limparCampos() {
    	this.medico = new Medico();
    }
    
    private void atualizarListaMedicos() {
    	medicos = MedicoDao.listar();
    }
    
    public MedicoBean() {
    	medico = new Medico();
    	MedicoDao = new MedicoDAO(JPAUtil.getEntityManager());
        atualizarListaMedicos();
    }
    
    
   
    
    public void excluir(Medico medico) {
        try {
        	MedicoDao.excluir(medico);
        	atualizarListaMedicos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("medico excluído com sucesso."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao excluir o medico.", null));
        }
    }
    
public void salvar() {
        try {
        	
        	
        	
        	if(medico.getId() == null ) {
        		
        		FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("medico salvo com sucesso."));
        		MedicoDao.salvar(medico);
        		
        		
        	}else {
        		
        		FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("medico editada com sucesso."));
        		MedicoDao.salvar(medico);
        	}
        	limparCampos();
            
        	
        	
            
            atualizarListaMedicos();
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar o medico.", null));
        }
    }
    
    
    public Medico getAgenda() {
		return medico;
	}


	public void setAgenda(Medico medico) {
		this.medico = medico;
	}


	public MedicoDAO getMedicoDao() {
		return MedicoDao;
	}


	public void setMedicoDao(MedicoDAO medicoDao) {
		MedicoDao = medicoDao;
	}


	public List<Medico> getMedicos() {
		return medicos;
	}


	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}


	public String editar(Medico medico) {
    	this.medico = medico;
		return "cadastroMedico";
	}


    
	
	
    
    
    
	

}
