package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
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

	@Past(message = "A data de nascimento deve estar no passado")
	@NotNull(message = "A data de nascimento deve ser informada")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@NotBlank(message = "O telefone deve ser informado")
	@Column(name = "telefone", length = 15, nullable = false)
	private String telefone;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_usuario", nullable = false, columnDefinition = "ENUM('ADMIN','FUNCIONARIO','CLIENTE')")
	private TipoUsuario tipoUsuario;

	@NotEmpty(message = "A senha deve ser informada")
	@Size(min = 8, message = "A senha deve ter no mínimo {min} caracteres")
	@Column(name = "senha_hash", length = 32, nullable = false)
	private String password;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id_endereco", nullable = false, unique = true)
	@Valid
	private Endereco endereco;

}
