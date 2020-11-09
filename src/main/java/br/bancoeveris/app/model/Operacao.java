package br.bancoeveris.app.model;

import javax.persistence.*;

@Entity
public class Operacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	private double valor;

	@ManyToOne
	@JoinColumn(name = "Conta_Origem")
	private Conta conta_origem;

	@ManyToOne
	@JoinColumn(name = "Conta_Destino")
	private Conta conta_destino;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Conta getConta_origem() {
		return conta_origem;
	}

	public void setConta_origem(Conta conta_origem) {
		this.conta_origem = conta_origem;
	}

	public Conta getConta_destino() {
		return conta_destino;
	}

	public void setConta_destino(Conta conta_destino) {
		this.conta_destino = conta_destino;
	}
}
