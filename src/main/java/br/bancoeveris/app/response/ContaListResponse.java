package br.bancoeveris.app.response;

import java.util.List;

import br.bancoeveris.app.model.Conta;

public class ContaListResponse extends BaseResponse {

	private List<Conta> Contas;

	public List<Conta> getContas() {
		return Contas;
	}

	public void setContas(List<Conta> contas) {
		Contas = contas;
	}
}
