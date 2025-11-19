package br.com.ucb.bancomalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ucb.bancomalvader.modelo.ContaPoupanca;

public interface ContaPoupancaRepository extends JpaRepository<ContaPoupanca, Long> {
}
