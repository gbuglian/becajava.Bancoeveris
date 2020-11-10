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
	private Conta contaOrigem;

	@ManyToOne
	@JoinColumn(name = "Conta_Destino")
	private Conta contaDestino;

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
		return contaOrigem;
	}

	public void setConta_origem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getConta_destino() {
		return contaDestino;
	}

	public void setConta_destino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
}
