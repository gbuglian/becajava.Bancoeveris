package br.bancoeveris.app.spec;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;

public class OperacaoSpec extends BaseResponse{

	private String tipo;
	private double valor;
	private Conta contaDestino;
	private String hash;
	
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
	public Conta getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
}
