package br.com.ucb.bancomalvader.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ucb.bancomalvader.modelo.Endereco;
import br.com.ucb.bancomalvader.modelo.TipoUsuario;
import br.com.ucb.bancomalvader.modelo.Usuario;
import br.com.ucb.bancomalvader.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final UsuarioRepository usuarioRepository;

	public UsuarioController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		Usuario usuario = new Usuario();
		usuario.setEndereco(new Endereco());
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastrar-usuario";
	}

	@PostMapping("/cadastrar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return "/usuario/cadastrar-usuario";
		}
		usuario.setTipoUsuario(TipoUsuario.ADMIN);
		usuarioRepository.save(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/usuario/novo";
	}

	@GetMapping("/listar")
	public String listarUsuario(Model model) {
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "/usuario/listar-usuario";
	}

	@GetMapping("/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id inválido:" + id));
		usuarioRepository.delete(usuario);
		return "redirect:/usuario/listar";
	}

	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model) {
		Optional<Usuario> usuarioVelho = usuarioRepository.findById(id);
		if (!usuarioVelho.isPresent()) {
			throw new IllegalArgumentException("Usuário inválido:" + id);
		}
		Usuario usuario = usuarioVelho.get();
		model.addAttribute("usuario", usuario);
		return "/usuario/alterar-usuario";
	}

	@PostMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id,
			@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			usuario.setId(id);
			return "/usuario/alterar-usuario";
		}
		usuario.setTipoUsuario(TipoUsuario.ADMIN);
		usuarioRepository.save(usuario);
		return "redirect:/usuario/listar";
	}

}
