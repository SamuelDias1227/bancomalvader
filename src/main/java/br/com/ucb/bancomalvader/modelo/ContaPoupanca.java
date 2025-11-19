package br.com.ucb.bancomalvader.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta_poupanca")
@PrimaryKeyJoinColumn(name = "id_conta")
public class ContaPoupanca extends Conta {

    @Column(name = "taxa_rendimento", nullable = false)
    private double taxaRendimento;

    @Column(name = "ultimo_rendimento")
    private LocalDateTime ultimoRendimento;

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public java.time.LocalDateTime getUltimoRendimento() {
        return ultimoRendimento;
    }

    public void setUltimoRendimento(java.time.LocalDateTime ultimoRendimento) {
        this.ultimoRendimento = ultimoRendimento;
    }

}
