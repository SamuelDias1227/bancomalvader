package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco", indexes = {
		@Index(name = "idx_endereco_cep", columnList = "cep")
})
@Getter
@Setter
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco")
	private Long id;

	@NotNull
	@Size(max = 10, message = "O CEP deve ter no máximo {max} caracteres")
	@Column(name = "cep", length = 10, nullable = false)
	private String cep;

	@NotNull
	@Size(max = 100, message = "O local deve ter no máximo {max} caracteres")
	@Column(name = "local", length = 100, nullable = false)
	private String local;

	@NotNull(message = "O número da casa deve ser informado")
	@Column(name = "numero_casa", nullable = false)
	private Integer numeroCasa;

	@NotNull
	@Size(max = 50)
	@Column(name = "bairro", length = 50, nullable = false)
	private String bairro;

	@NotNull
	@Size(max = 50)
	@Column(name = "cidade", length = 50, nullable = false)
	private String cidade;

	@NotNull
	@Size(max = 2, message = "Informe apenas a sigla")
	@Column(name = "estado", length = 2, nullable = false)
	private String estado;

	@Size(max = 50, message = "O local deve ter no máximo {max} caracteres")
	@Column(name = "complemento", length = 50)
	private String complemento;

}
