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
@RequestMapping("/conta-investimento")
public class ContaInvestimentoController {

    private final ContaInvestimentoRepository contaInvestimentoRepository;
    private final AgenciaRepository agenciaRepository;
    private final ClienteRepository clienteRepository;

    public ContaInvestimentoController(
            ContaInvestimentoRepository contaInvestimentoRepository,
            AgenciaRepository agenciaRepository,
            ClienteRepository clienteRepository) {

        this.contaInvestimentoRepository = contaInvestimentoRepository;
        this.agenciaRepository = agenciaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/novo")
    public String novaConta(Model model) {
        ContaInvestimento conta = new ContaInvestimento();
        conta.setSaldo(java.math.BigDecimal.ZERO);

        model.addAttribute("contaInvestimento", conta);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("perfisRisco", PerfilRisco.values());
        return "/conta-investimento/cadastrar-conta-investimento";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(
            @Valid ContaInvestimento conta,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "/conta-investimento/cadastrar-conta-investimento";
        }

        conta.setNumeroConta(gerarNumeroConta());
        conta.setDataAbertura(LocalDateTime.now());
        conta.setStatus(StatusConta.ATIVA);
        conta.setTipoConta(TipoConta.INVESTIMENTO);

        conta.setAgencia(
                agenciaRepository.findById(conta.getAgencia().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Agência inválida"))
        );

        conta.setCliente(
                clienteRepository.findById(conta.getCliente().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"))
        );

        contaInvestimentoRepository.save(conta);
        attributes.addFlashAttribute("mensagem", "Conta de Investimento cadastrada com sucesso!");
        return "redirect:/conta-investimento/novo";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("contas", contaInvestimentoRepository.findAll());
        return "/conta-investimento/listar-conta-investimento";
    }

    @GetMapping("/apagar/{id}")
    public String apagar(@PathVariable Long id, RedirectAttributes attributes) {
        ContaInvestimento conta = contaInvestimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida: " + id));

        contaInvestimentoRepository.delete(conta);
        attributes.addFlashAttribute("mensagem", "Conta apagada com sucesso!");
        return "redirect:/conta-investimento/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ContaInvestimento conta = contaInvestimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta inválida: " + id));

        model.addAttribute("contaInvestimento", conta);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("perfisRisco", PerfilRisco.values());
        return "/conta-investimento/alterar-conta-investimento";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @Valid ContaInvestimento conta,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            conta.setId(id);
            return "/conta-investimento/alterar-conta-investimento";
        }

        conta.setTipoConta(TipoConta.INVESTIMENTO);

        conta.setAgencia(
                agenciaRepository.findById(conta.getAgencia().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Agência inválida"))
        );

        conta.setCliente(
                clienteRepository.findById(conta.getCliente().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente inválida"))
        );

        contaInvestimentoRepository.save(conta);
        attributes.addFlashAttribute("mensagem", "Conta de Investimento atualizada com sucesso!");
        return "redirect:/conta-investimento/listar";
    }

    private String gerarNumeroConta() {
        SecureRandom random = new SecureRandom();
        String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        int rand = 1000 + random.nextInt(9000);
        return "CI" + data + "-" + rand;
    }
}
