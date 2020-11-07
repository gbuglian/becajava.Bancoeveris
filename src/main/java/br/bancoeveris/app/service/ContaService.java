package br.bancoeveris.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.spec.ContaSpec;

@Service
public class ContaService {

	final ContaRepository contaRepository;

	@Autowired
	public ContaService(ContaRepository Repository) {
		contaRepository = Repository;
	}

	public BaseResponse inserir(ContaSpec contaspec) {
		Conta conta = new Conta();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (contaspec.getHash() == conta.getHash()) {
	
			conta.setCpf(contaspec.getCpf());
			conta.setNome(contaspec.getNome());
			conta.setHash(contaspec.getHash());

			contaRepository.save(conta);
		}
		return response;
	}
}
