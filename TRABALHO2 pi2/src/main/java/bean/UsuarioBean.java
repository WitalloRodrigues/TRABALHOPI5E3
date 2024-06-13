package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.UsuarioDao;
import entidades.Usuario;
import util.JPAUtil;
import util.SessionUtils;

@ManagedBean
@ApplicationScoped
public class UsuarioBean {
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private UsuarioDao usuariodao;
	private LoginBean login;
	
	private void atualizarListaLancamentos() {
		usuarios = usuariodao.listar();
    }
	
	public UsuarioBean() {
		usuario = new Usuario();
		usuariodao = new UsuarioDao(JPAUtil.getEntityManager());
        atualizarListaLancamentos();
    }
	
	private void limparCampos() {
		usuario = new Usuario();
    }

    public void salvar() {
        try {
        	

        	
    		if(usuario.getId() == null ) {
        		
        		FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Usuário salvo com sucesso."));
        		usuariodao.salvar(usuario);
        		limparCampos();
        		
        	}else {
        		
        		
        		if (usuario.getSenha().isEmpty()) {
        	        Usuario usuarioExistente = usuariodao.buscarPorId(usuario.getId());
        	        
        	        System.out.println("baxo");
        	        System.out.println(usuarioExistente.getSenha());
            		System.out.println("cima");
            		
        	        usuario.setSenha(usuarioExistente.getSenha());
        	    }
        		
        		FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Usuário editado com sucesso."));
        		usuariodao.salvar(usuario);
        	}
            
        	
            limparCampos();
            atualizarListaLancamentos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar o lançamento.", null));
        }
    }
    
    public void excluir(Usuario usuario) {
        try {
        	usuariodao.excluir(usuario);
            atualizarListaLancamentos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Usuario excluído com sucesso."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao excluir o lançamento.", null));
        }
    }
    
    public String editarUsuario(Usuario usuario) {
        this.usuario = usuario;
        return "cadastro_usuario";
    }
    
    public void excluirUsuario(Usuario usuario) {
    	usuariodao.excluir(usuario);
        usuarios = usuariodao.listar();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário excluído com sucesso"));
    }
    
    public String listagem() {
    	limparCampos();
    	return "lista_usuarios";
    }
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioDao getUsuariodao() {
		return usuariodao;
	}

	public void setUsuariodao(UsuarioDao usuariodao) {
		this.usuariodao = usuariodao;
	}

}
