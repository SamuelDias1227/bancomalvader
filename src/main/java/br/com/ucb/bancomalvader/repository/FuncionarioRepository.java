package br.com.ucb.bancomalvader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ucb.bancomalvader.modelo.Cargo;
import br.com.ucb.bancomalvader.modelo.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByCargo(Cargo cargo);

}
