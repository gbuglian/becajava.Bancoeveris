package br.bancoeveris.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.service.ContaService;
import br.bancoeveris.app.spec.ContaList;
import br.bancoeveris.app.spec.ContaSpec;

@RestController
@RequestMapping("/Conta")
public class ContaController extends BaseController {

	private final ContaService service;

	public ContaController(ContaService _service) {
		service = _service;
	}

	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaSpec contaSpec) {
		try {
			BaseResponse response = service.inserir(contaSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na aplicação: Hash indisponível");
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity obter(@PathVariable Long id) {
		try {
			Conta response = service.obter(id);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@GetMapping
	public ResponseEntity listar() {
		try {
			ContaList contas = service.listar();
			return ResponseEntity.status(HttpStatus.OK).body(contas);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ContaSpec contaSpec, @PathVariable Long id) {
		try {
			BaseResponse response = service.atualizar(id, contaSpec);
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

}
