package br.com.ucb.bancomalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ucb.bancomalvader.modelo.Conta;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNumeroConta(String numeroConta);

    boolean existsByNumeroConta(String numeroConta);
}
