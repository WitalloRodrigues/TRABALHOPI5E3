package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Imc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double peso;
    private double altura;
    private double resultado;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente = new Paciente();
    
    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento = new Agendamento();

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Imc [id=" + id + ", peso=" + peso + ", altura=" + altura + ", resultado=" + resultado + ", paciente="
				+ paciente + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getResultado() {
		return resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
	
}
