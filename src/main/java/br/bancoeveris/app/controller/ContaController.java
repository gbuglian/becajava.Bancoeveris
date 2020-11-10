package br.bancoeveris.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.response.ContaListResponse;
import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.service.ContaService;

@RestController
@RequestMapping("/Conta")
public class ContaController extends BaseController {

	private final ContaService service;

	public ContaController(ContaService _service) {
		service = _service;
	}

	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaRequest contaRequest) {
		try {
			BaseResponse response = service.inserir(contaRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na aplicação: Hash indisponível");
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity obter(@PathVariable Long id) {
		try {
			ContaResponse response = service.obter(id);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@GetMapping
	public ResponseEntity listar() {
		try {
			ContaListResponse contas = service.listar();
			return ResponseEntity.status(HttpStatus.OK).body(contas);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ContaRequest contaRequest, @PathVariable Long id) {
		try {
			BaseResponse response = service.atualizar(id, contaRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity deletar(@PathVariable Long id) {
		try {
			BaseResponse response = service.deletar(id);
			return ResponseEntity.status(response.StatusCode).build();
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@GetMapping(path = "/saldo/{hash}")
	public ResponseEntity Saldo(@PathVariable String hash) {
		try {
			ContaResponse response = service.Saldo(hash);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {

			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

}
