package br.com.ucb.bancomalvader.controller;

import br.com.ucb.bancomalvader.modelo.Agencia;
import br.com.ucb.bancomalvader.modelo.Endereco;
import br.com.ucb.bancomalvader.repository.AgenciaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agencia")
public class AgenciaController {

    private final AgenciaRepository agenciaRepository;

    public AgenciaController(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    @GetMapping("/novo")
    public String adicionarAgencia(Model model) {
        Agencia agencia = new Agencia();
        agencia.setEndereco(new Endereco());
        model.addAttribute("agencia", agencia);
        return "/agencia/cadastrar-agencia";
    }

    @PostMapping("/cadastrar")
    public String salvarAgencia(@Valid Agencia agencia, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/agencia/cadastrar-agencia";
        }
        agenciaRepository.save(agencia);
        attributes.addFlashAttribute("mensagem", "Agência cadastrada com sucesso!");
        return "redirect:/agencia/novo";
    }

    @GetMapping("/listar")
    public String listarAgencias(Model model) {
        model.addAttribute("agencias", agenciaRepository.findAll());
        return "/agencia/listar-agencia";
    }

    @GetMapping("/apagar/{id}")
    public String apagarAgencia(@PathVariable("id") Long id, RedirectAttributes attributes) {
        Agencia agencia = agenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        agenciaRepository.delete(agencia);
        attributes.addFlashAttribute("mensagem", "Agência apagada com sucesso!");
        return "redirect:/agencia/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarAgencia(@PathVariable("id") Long id, Model model) {
        Agencia agencia = agenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("agencia", agencia);
        return "/agencia/alterar-agencia";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAgencia(@PathVariable("id") Long id,
            @Valid Agencia agencia, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            agencia.setId(id);
            return "/agencia/alterar-agencia";
        }
        agenciaRepository.save(agencia);
        attributes.addFlashAttribute("mensagem", "Agência atualizada com sucesso!");
        return "redirect:/agencia/listar";
    }
}