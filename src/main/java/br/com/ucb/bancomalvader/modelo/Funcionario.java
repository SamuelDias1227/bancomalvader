package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
public class Funcionario extends Usuario {

	@Column(name = "codigo_funcionario", length = 20, nullable = false, unique = true)
	private String codigoFuncionario;

	@Enumerated(EnumType.STRING)
	@Column(name = "cargo", nullable = false, columnDefinition = "ENUM('ESTAGIARIO','ATENDENTE','GERENTE')")
	private Cargo cargo;

	@ManyToOne
	@JoinColumn(name = "id_supervisor", nullable = true)
	private Funcionario supervisor;

	@ManyToOne
	@JoinColumn(name = "id_agencia", nullable = false)
	private Agencia agencia;

}
