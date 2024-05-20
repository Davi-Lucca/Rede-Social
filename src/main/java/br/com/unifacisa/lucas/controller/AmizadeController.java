package br.com.unifacisa.lucas.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.unifacisa.lucas.model.Usuario;
import br.com.unifacisa.lucas.service.UsuarioService;

@Controller
public class AmizadeController {

	private final UsuarioService usuarioService;

	public AmizadeController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping("/adicionar-amigo/{amigoId}")
	public String adicionarAmigo(@PathVariable Long amigoId, Authentication authentication) {
		Usuario amigo = usuarioService.getUsuarioById(amigoId);

		Usuario usuarioLogado = usuarioService.loadUserByUsername(authentication.getName());

		if (!amigo.equals(usuarioLogado) && !usuarioLogado.getAmigos().contains(amigo)) {
			usuarioService.adicionarAmigo(usuarioLogado, amigo);
		}

		return "redirect:/timeline";

	}

	@GetMapping("/amigos/sugestoes")
	public String getSugestoesDeAmigos(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Usuario usuarioAtual = this.usuarioService.loadUserByUsername(username);

		List<Usuario> sugestoes = usuarioService.getSugestoesDeAmigos(usuarioAtual.getId());
		model.addAttribute("sugestoes", sugestoes);
		return "sugestoes-amizade";
	}

}
