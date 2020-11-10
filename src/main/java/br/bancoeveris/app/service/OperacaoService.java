package br.bancoeveris.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.*;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.request.*;
import br.bancoeveris.app.response.BaseResponse;

@Service
public class OperacaoService {

	final OperacaoRepository operacaoRepository;
	final ContaRepository contaRepository;

	public OperacaoService(OperacaoRepository repository, ContaRepository _contaRepository) {

		operacaoRepository = repository;
		contaRepository = _contaRepository;
	}

	public BaseResponse depositar(OperacaoRequest operacaoRequest) {
		Conta conta = contaRepository.findByHash(operacaoRequest.getHash());
		Operacao operacao = new Operacao();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (operacaoRequest.getTipo() == "") {
			response.Message = "Para Depósitos, insira D";
			return response;
		}

		if (operacaoRequest.getValor() <= 0) {
			response.Message = "Valor para operação não informado";
			return response;
		}

		operacao.setTipo(operacaoRequest.getTipo());
		operacao.setValor(operacaoRequest.getValor());
		operacao.setConta_origem(conta);

		conta.setSaldo(conta.getSaldo() + operacaoRequest.getValor());

		contaRepository.save(conta);
		operacaoRepository.save(operacao);
		response.StatusCode = 200;
		response.Message = "Operação realizada com sucesso!";
		return response;
	}

	public BaseResponse sacar(OperacaoRequest operacaoRequestSaque) {
		Conta conta = contaRepository.findByHash(operacaoRequestSaque.getHash());

		Operacao operacao = new Operacao();
		BaseResponse response = new BaseResponse();
		response.StatusCode = 400;

		if (operacaoRequestSaque.getTipo() == "") {
			response.Message = "Para Saques, insira S";
			return response;
		}

		if (operacaoRequestSaque.getValor() <= 0) {
			response.Message = "Valor para operacao não informado";
		}

		if (operacaoRequestSaque.getValor() > conta.getSaldo()) {
			response.Message = "Saldo insuficiente";
			return response;
		}

		operacao.setValor(operacaoRequestSaque.getValor());
		operacao.setTipo(operacaoRequestSaque.getTipo());
		operacao.setConta_origem(conta);

		conta.setSaldo(conta.getSaldo() - operacaoRequestSaque.getValor());

		contaRepository.save(conta);
		operacaoRepository.save(operacao);

		response.StatusCode = 200;
		response.Message = "Operacao realizada com sucesso";
		return response;
	}

	public BaseResponse transferir(TransferenciaRequest operacaoRequest) {
		Conta contaO = contaRepository.findByHash(operacaoRequest.getHashOrigem());
		Conta contaD = contaRepository.findByHash(operacaoRequest.getHashDestino());

		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		response.StatusCode = 400;

		if (operacaoRequest.getValor() <= 0) {
			response.Message = "Valor para operação não informado";
		}

		if (operacaoRequest.getValor() > contaO.getSaldo()) {
			response.Message = "Saldo insuficiente";
			return response;
		}

		contaO.setSaldo(contaO.getSaldo() - operacaoRequest.getValor());
		contaD.setSaldo(contaD.getSaldo() + operacaoRequest.getValor());

		operacao.setConta_origem(contaO);
		operacao.setConta_destino(contaD);
		operacao.setValor(operacaoRequest.getValor());
		operacao.setTipo(operacaoRequest.getTipo());

		contaRepository.save(contaO);
		contaRepository.save(contaD);

		operacaoRepository.save(operacao);

		response.StatusCode = 200;
		response.Message = "Operação realizada com sucesso";
		return response;
	}

	public double Saldo(Long contaId) {

		double saldo = 0;

		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);

		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);

		List<Operacao> lista = operacaoRepository.findOperacoesPorConta(contaId);

		for (Operacao o : lista) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":
				if (o.getConta_destino().getId() == contaId) {

					saldo -= o.getValor();
					break;
				}
				if (o.getConta_origem().getId() == contaId) {
					saldo += o.getValor();
				}
				break;
			default:
				break;
			}
		}
		return saldo;
	}
}
