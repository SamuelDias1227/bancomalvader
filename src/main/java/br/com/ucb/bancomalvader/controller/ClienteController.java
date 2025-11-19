package br.com.ucb.bancomalvader.controller;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ucb.bancomalvader.modelo.Cliente;
import br.com.ucb.bancomalvader.modelo.Endereco;
import br.com.ucb.bancomalvader.modelo.TipoUsuario;
import br.com.ucb.bancomalvader.repository.ClienteRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/novo")
    public String adicionarCliente(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEndereco(new Endereco());
        model.addAttribute("cliente", cliente);
        return "/cliente/cadastrar-cliente";
    }

    @PostMapping("/cadastrar")
    public String salvarCliente(
            @Valid Cliente cliente,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "/cliente/cadastrar-cliente";
        }

        cliente.setTipoUsuario(TipoUsuario.CLIENTE);
        cliente.setCodigoCliente(gerarMatricula());

        clienteRepository.save(cliente);
        attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
        return "redirect:/cliente/novo";
    }

    @GetMapping("/listar")
    public String listarCliente(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "/cliente/listar-cliente";
    }

    @GetMapping("/apagar/{id}")
    public String apagarCliente(@PathVariable("id") long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + id));

        clienteRepository.delete(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") long id, Model model) {
        Optional<Cliente> clienteVelho = clienteRepository.findById(id);

        if (!clienteVelho.isPresent()) {
            throw new IllegalArgumentException("Cliente inválido: " + id);
        }

        model.addAttribute("cliente", clienteVelho.get());
        return "/cliente/alterar-cliente";
    }

    @PostMapping("/editar/{id}")
    public String editarCliente(
            @PathVariable("id") long id,
            @Valid Cliente cliente,
            BindingResult result) {

        if (result.hasErrors()) {
            cliente.setId(id);
            return "/cliente/alterar-cliente";
        }

        cliente.setTipoUsuario(TipoUsuario.CLIENTE);

        clienteRepository.save(cliente);
        return "redirect:/cliente/listar";
    }

    private String gerarMatricula() {
        SecureRandom random = new SecureRandom();
        LocalDate hoje = LocalDate.now();
        String anoMes = hoje.format(DateTimeFormatter.ofPattern("yyyyMM"));
        int numeroAleatorio = 100 + random.nextInt(900);
        return String.format("CLI%s-%03d", anoMes, numeroAleatorio);
    }

}
