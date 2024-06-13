package entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Agendamento {

	@Override
	public String toString() {
		return "Agendamento [id=" + id + ", status=" + status + ", clinica=" + clinica + ", medico=" + medico
				+ ", paciente=" + paciente + ", dataHoraAgendamento=" + dataHoraAgendamento + ", dataCadastro="
				+ dataCadastro + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Status status;
	private String clinica;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "medico_id")
	private Medico medico = new Medico();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paciente_id")
	private Paciente paciente = new Paciente();
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora_agendamento")
    private Date dataHoraAgendamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
	
	public Agendamento() {
		this.status = Status.AGENDADO;
		this.dataCadastro = new Date(); 
    }
	
	 
    public Paciente getPaciente() {
		return paciente;
	}


	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getClinica() {
		return clinica;
	}

	public void setClinica(String clinica) {
		this.clinica = clinica;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Date getDataHoraAgendamento() {
		return dataHoraAgendamento;
	}

	public void setDataHoraAgendamento(Date dataHoraAgendamento) {
		this.dataHoraAgendamento = dataHoraAgendamento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	
	
}
