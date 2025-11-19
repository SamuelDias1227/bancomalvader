package br.com.ucb.bancomalvader.controller;

import br.com.ucb.bancomalvader.modelo.Cargo;
import br.com.ucb.bancomalvader.modelo.Endereco;
import br.com.ucb.bancomalvader.modelo.Funcionario;
import br.com.ucb.bancomalvader.modelo.TipoUsuario;
import br.com.ucb.bancomalvader.repository.AgenciaRepository;
import br.com.ucb.bancomalvader.repository.FuncionarioRepository;
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
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;
    private final AgenciaRepository agenciaRepository;

    public FuncionarioController(FuncionarioRepository funcionarioRepository,
            AgenciaRepository agenciaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.agenciaRepository = agenciaRepository;
    }

    @GetMapping("/novo")
    public String adicionarFuncionario(Model model) {
        Funcionario funcionario = new Funcionario();
        funcionario.setEndereco(new Endereco());
        funcionario.setTipoUsuario(TipoUsuario.FUNCIONARIO);

        model.addAttribute("funcionario", funcionario);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("supervisores",
                funcionarioRepository.findByCargo(Cargo.GERENTE));
        return "/funcionario/cadastrar-funcionario";
    }

    @PostMapping("/cadastrar")
    public String salvarFuncionario(@Valid Funcionario funcionario,
            BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/funcionario/cadastrar-funcionario";
        }

        funcionario.setTipoUsuario(TipoUsuario.FUNCIONARIO);

        if (funcionario.getAgencia() != null && funcionario.getAgencia().getId() != null) {
            funcionario.setAgencia(
                    agenciaRepository.findById(funcionario.getAgencia().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Agência inválida!")));
        }

        if (funcionario.getSupervisor() != null && funcionario.getSupervisor().getId() != null) {
            funcionario.setSupervisor(
                    funcionarioRepository.findById(funcionario.getSupervisor().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Supervisor inválido!")));
        } else {
            funcionario.setSupervisor(null);
        }

        funcionarioRepository.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
        return "redirect:/funcionario/novo";
    }

    @GetMapping("/listar")
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "/funcionario/listar-funcionario";
    }

    @GetMapping("/apagar/{id}")
    public String apagarFuncionario(@PathVariable("id") Long id, RedirectAttributes attributes) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        funcionarioRepository.delete(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionário apagado com sucesso!");
        return "redirect:/funcionario/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable("id") Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        model.addAttribute("funcionario", funcionario);
        model.addAttribute("agencias", agenciaRepository.findAll());
        model.addAttribute("supervisores",
                funcionarioRepository.findByCargo(Cargo.GERENTE));
        return "/funcionario/alterar-funcionario";
    }

    @PostMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable("id") Long id,
            @Valid Funcionario funcionario,
            BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            funcionario.setId(id);
            return "/funcionario/alterar-funcionario";
        }

        funcionario.setTipoUsuario(TipoUsuario.FUNCIONARIO);

        if (funcionario.getAgencia() != null && funcionario.getAgencia().getId() != null) {
            funcionario.setAgencia(
                    agenciaRepository.findById(funcionario.getAgencia().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Agência inválida!")));
        }

        if (funcionario.getSupervisor() != null && funcionario.getSupervisor().getId() != null) {
            funcionario.setSupervisor(
                    funcionarioRepository.findById(funcionario.getSupervisor().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Supervisor inválido!")));
        } else {
            funcionario.setSupervisor(null);
        }

        funcionarioRepository.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionário atualizado com sucesso!");
        return "redirect:/funcionario/listar";
    }
}