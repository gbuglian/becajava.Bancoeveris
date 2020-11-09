package br.bancoeveris.app.spec;

public class TransferenciaSpec {

	private String hashOrigem;
	private String hashDestino;
	private Double valor;
	private String tipo;

	public String getHashOrigem() {
		return hashOrigem;
	}

	public void setHashOrigem(String hashOrigem) {
		this.hashOrigem = hashOrigem;
	}

	public String getHashDestino() {
		return hashDestino;
	}

	public void setHashDestino(String hashDestino) {
		this.hashDestino = hashDestino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
