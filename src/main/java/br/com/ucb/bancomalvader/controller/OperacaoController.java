package br.com.ucb.bancomalvader.controller;

import br.com.ucb.bancomalvader.modelo.Conta;
import br.com.ucb.bancomalvader.modelo.TipoTransacao;
import br.com.ucb.bancomalvader.modelo.Transacao;
import br.com.ucb.bancomalvader.repository.ContaRepository;
import br.com.ucb.bancomalvader.repository.TransacaoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/operacao")
public class OperacaoController {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public OperacaoController(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping("/deposito")
    public String formularioDeposito(Model model) {
        model.addAttribute("contas", contaRepository.findAll());
        return "/operacoes/operacao-deposito";
    }

    @PostMapping("/deposito")
    public String realizarDeposito(
            @RequestParam Long contaId,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao,
            RedirectAttributes attributes) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida"));

        Transacao transacao = new Transacao();
        transacao.setContaOrigem(null);
        transacao.setContaDestino(conta);
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setValor(valor);
        transacao.setDescricao(descricao);

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        transacaoRepository.save(transacao);
        attributes.addFlashAttribute("mensagem", "Depósito realizado com sucesso!");
        return "redirect:/operacao/deposito";
    }

    @GetMapping("/saque")
    public String formularioSaque(Model model) {
        model.addAttribute("contas", contaRepository.findAll());
        return "/operacoes/operacao-saque";
    }

    @PostMapping("/saque")
    public String realizarSaque(
            @RequestParam Long contaId,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao,
            RedirectAttributes attributes) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida"));

        if (conta.getSaldo().compareTo(valor) < 0) {
            attributes.addFlashAttribute("erro", "Saldo insuficiente!");
            return "redirect:/operacao/saque";
        }

        Transacao transacao = new Transacao();
        transacao.setContaOrigem(conta);
        transacao.setContaDestino(null);
        transacao.setTipoTransacao(TipoTransacao.SAQUE);
        transacao.setValor(valor);
        transacao.setDescricao(descricao);

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        transacaoRepository.save(transacao);
        attributes.addFlashAttribute("mensagem", "Saque realizado com sucesso!");
        return "redirect:/operacao/saque";
    }

    @GetMapping("/transferencia")
    public String formularioTransferencia(Model model) {
        model.addAttribute("contas", contaRepository.findAll());
        return "/operacoes/operacao-transferencia";
    }

    @PostMapping("/transferencia")
    public String realizarTransferencia(
            @RequestParam Long contaOrigemId,
            @RequestParam Long contaDestinoId,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao,
            RedirectAttributes attributes) {
        Conta origem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem inválida"));
        Conta destino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino inválida"));

        if (origem.getSaldo().compareTo(valor) < 0) {
            attributes.addFlashAttribute("erro", "Saldo insuficiente!");
            return "redirect:/operacao/transferencia";
        }

        Transacao transacao = new Transacao();
        transacao.setContaOrigem(origem);
        transacao.setContaDestino(destino);
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        transacao.setValor(valor);
        transacao.setDescricao(descricao);

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        contaRepository.save(origem);
        contaRepository.save(destino);
        transacaoRepository.save(transacao);

        attributes.addFlashAttribute("mensagem", "Transferência realizada com sucesso!");
        return "redirect:/operacao/transferencia";
    }

    @GetMapping("/extrato/{contaId}")
    public String extrato(@PathVariable Long contaId, Model model) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida"));

        List<Transacao> transacoes = transacaoRepository.findByContaOrigemOrContaDestinoOrderByDataHoraDesc(conta,
                conta);

        model.addAttribute("conta", conta);
        model.addAttribute("transacoes", transacoes);
        return "/operacoes/extrato-conta";
    }
}
