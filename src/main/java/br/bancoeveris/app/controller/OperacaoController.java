package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.OperacaoService;

@RestController
@RequestMapping("/contas")
public class OperacaoController {

	@Autowired
	private OperacaoService service;

	@PostMapping(path = "/depositos")
	public ResponseEntity depositar(@RequestBody OperacaoRequest operacaoRequest) {
		try {
			BaseResponse response = service.depositar(operacaoRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}

	@PostMapping(path = "/saques")
	public ResponseEntity sacar(@RequestBody OperacaoRequest operacaoRequest) {
		try {
			BaseResponse response = service.sacar(operacaoRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}

	@PostMapping(path = "/Transferencias")
	public ResponseEntity transferir(@RequestBody TransferenciaRequest transferenciaRequest) {
		try {
			BaseResponse response = service.transferir(transferenciaRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}
}
