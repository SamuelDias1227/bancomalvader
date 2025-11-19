package br.com.ucb.bancomalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ucb.bancomalvader.modelo.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {
}
