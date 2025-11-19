package br.com.ucb.bancomalvader.controller;

import br.com.ucb.bancomalvader.modelo.*;
import br.com.ucb.bancomalvader.repository.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/conta-corrente")
public class ContaCorrenteController {

    private final ContaCorrenteRepository contaCorrenteRepository;
    private final AgenciaRepository agenciaRepository;
    private final ClienteRepository clienteRepository;

    public ContaCorrenteController(
            ContaCorrenteRepository contaCorrenteRepository,
            AgenciaRepository agenciaRepository,
            ClienteRepository clienteRepository) {

        this.contaCorrenteRepository = contaCorrenteRepository;
        this.agenciaRepository = agenciaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/novo")
    public String novaConta(Model model) {
        ContaCorrente conta = new ContaCorrente();
        conta.setSaldo(BigDecimal.ZERO);

        model.addAttribute("contaCorrente", conta);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "/conta-corrente/cadastrar-conta-corrente";
    }

    @PostMapping("/cadastrar")
    public String cadastrarConta(
            @Valid ContaCorrente conta,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "/conta-corrente/cadastrar-conta-corrente";
        }

        conta.setNumeroConta(gerarNumeroConta());
        conta.setDataAbertura(LocalDateTime.now());
        conta.setStatus(StatusConta.ATIVA);
        conta.setTipoConta(TipoConta.CORRENTE);

        conta.setAgencia(
                agenciaRepository.findById(conta.getAgencia().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Agência inválida"))
        );

        conta.setCliente(
                clienteRepository.findById(conta.getCliente().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"))
        );

        contaCorrenteRepository.save(conta);
        attributes.addFlashAttribute("mensagem", "Conta Corrente cadastrada com sucesso!");
        return "redirect:/conta-corrente/novo";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("contas", contaCorrenteRepository.findAll());
        return "/conta-corrente/listar-conta-corrente";
    }

    @GetMapping("/apagar/{id}")
    public String apagar(@PathVariable Long id, RedirectAttributes attributes) {
        ContaCorrente conta = contaCorrenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida: " + id));

        contaCorrenteRepository.delete(conta);
        attributes.addFlashAttribute("mensagem", "Conta apagada com sucesso!");
        return "redirect:/conta-corrente/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ContaCorrente conta = contaCorrenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida: " + id));

        model.addAttribute("contaCorrente", conta);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "/conta-corrente/alterar-conta-corrente";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid ContaCorrente conta,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            conta.setId(id);
            return "/conta-corrente/alterar-conta-corrente";
        }

        conta.setTipoConta(TipoConta.CORRENTE);

        conta.setAgencia(
                agenciaRepository.findById(conta.getAgencia().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Agência inválida"))
        );

        conta.setCliente(
                clienteRepository.findById(conta.getCliente().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"))
        );

        contaCorrenteRepository.save(conta);
        attributes.addFlashAttribute("mensagem", "Conta Corrente atualizada com sucesso!");
        return "redirect:/conta-corrente/listar";
    }

    private String gerarNumeroConta() {
        SecureRandom random = new SecureRandom();
        String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        int rand = 1000 + random.nextInt(9000);
        return "CC" + data + "-" + rand;
    }
}

