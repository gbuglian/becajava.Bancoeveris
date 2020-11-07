package br.bancoeveris.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.bancoeveris.app.model.Operacao;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

}
