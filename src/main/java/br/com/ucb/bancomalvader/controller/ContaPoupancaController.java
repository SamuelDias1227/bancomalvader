package br.com.ucb.bancomalvader.controller;

import br.com.ucb.bancomalvader.modelo.*;
import br.com.ucb.bancomalvader.repository.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/conta-poupanca")
public class ContaPoupancaController {

    private final ContaPoupancaRepository contaPoupancaRepository;
    private final AgenciaRepository agenciaRepository;
    private final ClienteRepository clienteRepository;

    public ContaPoupancaController(
            ContaPoupancaRepository contaPoupancaRepository,
            AgenciaRepository agenciaRepository,
            ClienteRepository clienteRepository) {

        this.contaPoupancaRepository = contaPoupancaRepository;
        this.agenciaRepository = agenciaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/novo")
    public String novaConta(Model model) {
        ContaPoupanca conta = new ContaPoupanca();
        conta.setSaldo(java.math.BigDecimal.ZERO);

        model.addAttribute("contaPoupanca", conta);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "/conta-poupanca/cadastrar-conta-poupanca";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(
            @Valid ContaPoupanca conta,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "/conta-poupanca/cadastrar-conta-poupanca";
        }

        conta.setNumeroConta(gerarNumeroConta());
        conta.setDataAbertura(LocalDateTime.now());
        conta.setStatus(StatusConta.ATIVA);
        conta.setTipoConta(TipoConta.POUPANCA);

        conta.setAgencia(
                agenciaRepository.findById(conta.getAgencia().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Agência inválida"))
        );

        conta.setCliente(
                clienteRepository.findById(conta.getCliente().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"))
        );

        contaPoupancaRepository.save(conta);
        attributes.addFlashAttribute("mensagem", "Conta Poupança cadastrada com sucesso!");
        return "redirect:/conta-poupanca/novo";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("contas", contaPoupancaRepository.findAll());
        return "/conta-poupanca/listar-conta-poupanca";
    }

    @GetMapping("/apagar/{id}")
    public String apagar(@PathVariable Long id, RedirectAttributes attributes) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida: " + id));

        contaPoupancaRepository.delete(conta);
        attributes.addFlashAttribute("mensagem", "Conta apagada com sucesso!");
        return "redirect:/conta-poupanca/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida: " + id));

        model.addAttribute("contaPoupanca", conta);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "/conta-poupanca/alterar-conta-poupanca";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid ContaPoupanca conta,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            conta.setId(id);
            return "/conta-poupanca/alterar-conta-poupanca";
        }

        conta.setTipoConta(TipoConta.POUPANCA);

        conta.setAgencia(
                agenciaRepository.findById(conta.getAgencia().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Agência inválida"))
        );

        conta.setCliente(
                clienteRepository.findById(conta.getCliente().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"))
        );

        contaPoupancaRepository.save(conta);
        attributes.addFlashAttribute("mensagem", "Conta Poupança atualizada com sucesso!");
        return "redirect:/conta-poupanca/listar";
    }

    private String gerarNumeroConta() {
        SecureRandom random = new SecureRandom();
        String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        int rand = 1000 + random.nextInt(9000);
        return "CP" + data + "-" + rand;
    }
}
