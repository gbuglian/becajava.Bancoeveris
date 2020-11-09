package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.service.OperacaoService;
import br.bancoeveris.app.spec.OperacaoSpec;
import br.bancoeveris.app.spec.TransferenciaSpec;

@RestController
@RequestMapping("/contas")
public class OperacaoController {

	@Autowired
	private OperacaoService service;

	@PostMapping(path = "/depositos")
	public ResponseEntity depositar(@RequestBody OperacaoSpec operacaoSpec) {
		try {
			BaseResponse response = service.depositar(operacaoSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}

	@PostMapping(path = "/saques")
	public ResponseEntity sacar(@RequestBody OperacaoSpec operacaoSpec) {
		try {
			BaseResponse response = service.sacar(operacaoSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}

	@PostMapping(path = "/Transferencias")
	public ResponseEntity transferir(@RequestBody TransferenciaSpec operacaoSpec) {
		try {
			BaseResponse response = service.transferir(operacaoSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}
}
