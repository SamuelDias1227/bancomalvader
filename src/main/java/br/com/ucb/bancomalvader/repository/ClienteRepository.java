package br.com.ucb.bancomalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ucb.bancomalvader.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
