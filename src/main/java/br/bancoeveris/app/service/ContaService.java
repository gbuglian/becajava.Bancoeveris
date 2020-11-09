package br.bancoeveris.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.spec.ContaList;
import br.bancoeveris.app.spec.ContaSpec;

@Service
public class ContaService {

	final ContaRepository contaRepository;
	final OperacaoService operacaoService;

	public ContaService(ContaRepository Repository, OperacaoService _operacaoService) {
		contaRepository = Repository;
		operacaoService = _operacaoService;
	}

	public BaseResponse inserir(ContaSpec contaspec) {
		Conta conta = new Conta();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (contaspec.getHash() == conta.getHash()) {
			response.Message = "Hash já em uso";
			return response;
		}

		if (contaspec.getHash() == "") {
			response.Message = "Hash não preenchido";
			return response;
		}

		if (contaspec.getNome() == "") {
			response.Message = "Nome não preenchido";
			return response;
		}

		if (contaspec.getCpf() == "") {
			response.Message = "CPF não preenchido";
			return response;
		}

		conta.setHash(contaspec.getHash());
		conta.setCpf(contaspec.getCpf());
		conta.setNome(contaspec.getNome());

		contaRepository.save(conta);

		response.StatusCode = 201;
		response.Message = "Conta inserida com sucesso.";
		return response;
	}

	public Conta obter(Long id) {
		Optional<Conta> cliente = contaRepository.findById(id);

		Conta response = new Conta();

		if (cliente.isEmpty()) {
			response.Message = "Cliente não encontrado";
			response.StatusCode = 404;
			return response;
		}
		response.setHash(cliente.get().getHash());
		response.setId(cliente.get().getId());
		response.setNome(cliente.get().getNome());
		response.setCpf(cliente.get().getCpf());
		response.setSaldo(cliente.get().getSaldo());
		response.Message = "Cliente obtido com sucesso";
		response.StatusCode = 200;
		return response;
	}

	public ContaList listar() {
		List<Conta> lista = contaRepository.findAll();

		ContaList response = new ContaList();
		response.setContas(lista);
		response.StatusCode = 200;
		response.Message = "Contas Obtidas com sucesso";

		return response;
	}

	public BaseResponse atualizar(Long id, ContaSpec contaSpec) {
		Conta conta = new Conta();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (contaSpec.getHash() == "") {
			response.Message = "Hash não informado";
			return response;
		}

		if (contaSpec.getNome() == "") {
			response.Message = "Nome não informado";
			return response;
		}

		if (contaSpec.getCpf() == "") {
			response.Message = "CPF não informado";
			return response;
		}

		conta.setId(id);
		conta.setHash(contaSpec.getHash());
		conta.setNome(contaSpec.getNome());
		conta.setCpf(contaSpec.getCpf());

		contaRepository.save(conta);
		response.StatusCode = 200;
		response.Message = "Conta Atualizada com sucesso";
		return response;
	}

	public BaseResponse deletar(Long id) {
		BaseResponse response = new BaseResponse();

		if (id == null || id == 0) {
			response.StatusCode = 400;
			return response;
		}

		contaRepository.deleteById(id);
		response.StatusCode = 200;
		return response;
	}
}
