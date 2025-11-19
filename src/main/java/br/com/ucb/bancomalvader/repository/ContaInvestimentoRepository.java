package br.com.ucb.bancomalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ucb.bancomalvader.modelo.ContaInvestimento;

public interface ContaInvestimentoRepository extends JpaRepository<ContaInvestimento, Long> {
}
