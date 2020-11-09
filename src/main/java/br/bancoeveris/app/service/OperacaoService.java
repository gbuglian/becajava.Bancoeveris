package br.bancoeveris.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.*;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.spec.*;

@Service
public class OperacaoService {

	final OperacaoRepository operacaoRepository;
	final ContaRepository contaRepository;

	public OperacaoService(OperacaoRepository repository, ContaRepository _contaRepository) {

		operacaoRepository = repository;
		contaRepository = _contaRepository;
	}

	public BaseResponse depositar(OperacaoSpec operacaoSpec) {
		Optional<Conta> conta = contaRepository.findByHash(operacaoSpec.getHash());

		Operacao operacao = new Operacao();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (!conta.isPresent()) {
			response.Message = "Conta não encontrada";
		}

		if (operacaoSpec.getTipo() == "") {
			response.Message = "Para Depósitos, insira D";
			return response;
		}

		if (operacaoSpec.getValor() <= 0) {
			response.Message = "Valor para operação não informado";
			return response;
		}

		operacao.setTipo(operacaoSpec.getTipo());
		operacao.setValor(operacaoSpec.getValor());
		operacao.setConta_origem(conta.get());

		conta.get().setSaldo(conta.get().getSaldo() + operacaoSpec.getValor());

		contaRepository.save(conta.get());
		operacaoRepository.save(operacao);
		response.StatusCode = 200;
		response.Message = "Operação realizada com sucesso!";
		return response;
	}

	public BaseResponse sacar(OperacaoSpec operacaoSpecSaque) {
		Optional<Conta> conta = contaRepository.findByHash(operacaoSpecSaque.getHash());

		Operacao operacao = new Operacao();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (!conta.isPresent()) {
			response.Message = "Conta não encontrada";
			return response;
		}

		if (operacaoSpecSaque.getTipo() == "") {
			response.Message = "Para Saques, insira S";
			return response;
		}

		if (operacaoSpecSaque.getValor() <= 0) {
			response.Message = "Valor para operacao não informado";
		}
		
		if(operacaoSpecSaque.getValor() > conta.get().getSaldo()) {
			response.Message = "Saldo insuficiente";
			return response;
		}

		operacao.setValor(operacaoSpecSaque.getValor());
		operacao.setTipo(operacaoSpecSaque.getTipo());
		operacao.setConta_origem(conta.get());

		conta.get().setSaldo(conta.get().getSaldo() - operacaoSpecSaque.getValor());

		contaRepository.save(conta.get());
		operacaoRepository.save(operacao);

		response.StatusCode = 200;
		response.Message = "Operacao realizada com sucesso";
		return response;
	}

	public BaseResponse transferir(TransferenciaSpec operacaoSpec) {
		Optional<Conta> contaO = contaRepository.findByHash(operacaoSpec.getHashOrigem());
		Optional<Conta> contaD = contaRepository.findByHash(operacaoSpec.getHashDestino());

		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		response.StatusCode = 400;

		if (!contaO.isPresent()) {
			response.Message = "Conta Origem não localizada";
			return response;
		}

		if (!contaD.isPresent()) {
			response.Message = "Conta Destino não localizada";
		}

		if (operacaoSpec.getValor() <= 0) {
			response.Message = "Valor para operação não informado";
		}
		
		if(operacaoSpec.getValor() > contaO.get().getSaldo()) {
			response.Message = "Saldo insuficiente";
			return response;
		}

		contaO.get().setSaldo(contaO.get().getSaldo() - operacaoSpec.getValor());
		contaD.get().setSaldo(contaD.get().getSaldo() + operacaoSpec.getValor());
		
		operacao.setConta_origem(contaO.get());
		operacao.setConta_destino(contaD.get());
		operacao.setValor(operacaoSpec.getValor());
		operacao.setTipo(operacaoSpec.getTipo());
		
		contaRepository.save(contaO.get());
		contaRepository.save(contaD.get());

		operacaoRepository.save(operacao);

		response.StatusCode = 200;
		response.Message = "Operação realizada com sucesso";
		return response;
	}
	
}
