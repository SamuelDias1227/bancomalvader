package br.com.ucb.bancomalvader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ucb.bancomalvader.modelo.Conta;
import br.com.ucb.bancomalvader.modelo.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaOrigemOrContaDestinoOrderByDataHoraDesc(Conta origem, Conta destino);
}
