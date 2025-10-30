package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "id_usuario")
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

	public String getCodigoFuncionario() {
		return codigoFuncionario;
	}
	public void setCodigoFuncionario(String codigoFuncionario) {
		this.codigoFuncionario = codigoFuncionario;
	}

	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Funcionario getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Funcionario supervisor) {
		this.supervisor = supervisor;
	}

	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
}
