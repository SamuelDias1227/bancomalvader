package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
public class Cliente extends Usuario {

    @Column(name = "codigo_cliente", length = 20, nullable = false, unique = true)
    private String codigoCliente;

    @Column(name = "score_credito", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private double scoreDeCredito;

}
