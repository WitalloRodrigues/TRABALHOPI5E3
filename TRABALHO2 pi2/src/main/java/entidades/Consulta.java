package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Consulta {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String queixaPrincipal;
    private String diagnostico;

    
    @OneToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento = new Agendamento();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getQueixaPrincipal() {
		return queixaPrincipal;
	}


	public void setQueixaPrincipal(String queixaPrincipal) {
		this.queixaPrincipal = queixaPrincipal;
	}


	public String getDiagnostico() {
		return diagnostico;
	}


	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}


	public Agendamento getAgendamento() {
		return agendamento;
	}


	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}


	@Override
	public String toString() {
		return "Consulta [id=" + id + ", queixaPrincipal=" + queixaPrincipal + ", diagnostico=" + diagnostico
				+ ", agendamento=" + agendamento + "]";
	}

	
    
    
    
    
}
