package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.bancoeveris.app.service.ContaService;
import br.bancoeveris.app.spec.ContaSpec;

@RestController
@RequestMapping("/Conta")
public class ContaController {
	
	private final ContaService service;
	
	@Autowired
	public ContaController(ContaService _service) {
		service = _service;
	}
	
	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaSpec contaSpec) {
		try {
				service.inserir(contaSpec);
				return ResponseEntity.status(HttpStatus.CREATED).body("Conta Inserida com Sucesso");
		}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na aplicação");
		}
	}

}
