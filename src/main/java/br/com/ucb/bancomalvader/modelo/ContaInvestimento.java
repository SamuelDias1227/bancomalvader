package br.com.ucb.bancomalvader.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta_investimento")
@PrimaryKeyJoinColumn(name = "id_conta")
public class ContaInvestimento extends Conta {

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_risco", nullable = false, columnDefinition = "ENUM('BAIXO','MEDIO','ALTO')")
    private PerfilRisco perfilRisco;

    @Column(name = "valor_minimo", nullable = false)
    private double valorMinimo;

    @Column(name = "taxa_rendimento_base", nullable = false)
    private double taxaRendimentoBase;

    public PerfilRisco getPerfilRisco() {
        return perfilRisco;
    }

    public void setPerfilRisco(PerfilRisco perfilRisco) {
        this.perfilRisco = perfilRisco;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public double getTaxaRendimentoBase() {
        return taxaRendimentoBase;
    }

    public void setTaxaRendimentoBase(double taxaRendimentoBase) {
        this.taxaRendimentoBase = taxaRendimentoBase;
    }

}
