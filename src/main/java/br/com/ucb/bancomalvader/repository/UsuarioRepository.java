package br.com.ucb.bancomalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ucb.bancomalvader.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
