package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
    name = "endereco",
    indexes = {
        @Index(name = "idx_endereco_cep", columnList = "cep")
    }
)
public class Endereco {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", nullable = true)
    private Usuario usuario;

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

	public Long getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}

	public Integer getNumeroCasa() {
		return numeroCasa;
	}
	public void setNumeroCasa(Integer numeroCasa) {
		this.numeroCasa = numeroCasa;
	}

	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
    
}
