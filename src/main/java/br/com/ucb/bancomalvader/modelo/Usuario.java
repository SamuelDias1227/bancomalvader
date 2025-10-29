package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@NotNull
	@Size(min = 2, message = "O nome deve ter no mínimo {min} carateres")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@CPF(message = "CPF inválido")
	@Column(name = "cpf", length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "telefone", length = 15, nullable = false)
    private String telefone;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, columnDefinition = "ENUM('FUNCIONARIO','CLIENTE')")
    private TipoUsuario tipoUsuario;
    
    @NotEmpty(message = "A senha deve ser informada")
    @Size(min = 8, message = "A senha deve ter no mínimo {min} caracteres")
    @Column(name = "senha_hash", length = 32, nullable = false)
    private String password;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
