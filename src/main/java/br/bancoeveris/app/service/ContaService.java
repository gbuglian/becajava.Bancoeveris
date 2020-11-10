package br.bancoeveris.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaListResponse;
import br.bancoeveris.app.response.ContaResponse;

@Service
public class ContaService {

	final ContaRepository contaRepository;
	final OperacaoService operacaoService;

	public ContaService(ContaRepository Repository, OperacaoService _operacaoService) {
		contaRepository = Repository;
		operacaoService = _operacaoService;
	}

	public BaseResponse inserir(ContaRequest contaRequest) {
		Conta conta = new Conta();
		ContaResponse response = new ContaResponse();
		response.StatusCode = 400;

		if (contaRequest.getHash() == conta.getHash()) {
			response.Message = "Hash já em uso";
			return response;
		}

		if (contaRequest.getHash() == "") {
			response.Message = "Hash não preenchido";
			return response;
		}

		if (contaRequest.getNome() == "") {
			response.Message = "Nome não preenchido";
			return response;
		}

		if (contaRequest.getCpf() == "") {
			response.Message = "CPF não preenchido";
			return response;
		}

		UUID uuid = UUID.randomUUID();
		conta.setHash(uuid.toString());

		response.Message = "Hash gerado";

		conta.setCpf(contaRequest.getCpf());
		conta.setNome(contaRequest.getNome());

		contaRepository.save(conta);

		response.setHash(conta.getHash());
		response.setNome(conta.getNome());
		response.setId(conta.getId());

		response.StatusCode = 201;
		response.Message = "Conta inserida com sucesso.";

		return response;
	}

	public ContaResponse obter(Long id) {
		Optional<Conta> cliente = contaRepository.findById(id);

		ContaResponse response = new ContaResponse();

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

	public ContaListResponse listar() {
		List<Conta> lista = contaRepository.findAll();

		ContaListResponse response = new ContaListResponse();
		response.setContas(lista);
		response.StatusCode = 200;
		response.Message = "Contas Obtidas com sucesso";

		return response;
	}

	public BaseResponse atualizar(Long id, ContaRequest contaRequest) {
		Conta conta = new Conta();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (contaRequest.getHash() == "") {
			response.Message = "Hash não informado";
			return response;
		}

		if (contaRequest.getNome() == "") {
			response.Message = "Nome não informado";
			return response;
		}

		if (contaRequest.getCpf() == "") {
			response.Message = "CPF não informado";
			return response;
		}

		conta.setId(id);
		conta.setHash(contaRequest.getHash());
		conta.setNome(contaRequest.getNome());
		conta.setCpf(contaRequest.getCpf());

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

	public ContaResponse Saldo(String hash) {

		ContaResponse response = new ContaResponse();
		response.StatusCode = 400;

		Conta conta = contaRepository.findByHash(hash);

		if (conta == null) {
			response.Message = "Conta não encontrada!!";
			return response;
		}

		double saldo = operacaoService.Saldo(conta.getId());

		response.setSaldo(saldo);
		response.setNome(conta.getNome());
		response.setHash(conta.getHash());
		response.Message = "Saldo obtido com sucesso";
		response.StatusCode = 200;
		return response;
	}
}
