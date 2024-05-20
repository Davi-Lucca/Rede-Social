package br.com.unifacisa.lucas.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.unifacisa.lucas.model.Postagem;
import br.com.unifacisa.lucas.model.Usuario;
import br.com.unifacisa.lucas.service.PostagemService;
import br.com.unifacisa.lucas.service.UsuarioService;

@Controller
public class HomeController {

	private final UsuarioService usuarioService;
	private final PostagemService postagemService;

	public HomeController(UsuarioService usuarioService, PostagemService postagemService) {
		this.usuarioService = usuarioService;
		this.postagemService = postagemService;
	}

	@GetMapping("/home")
	public String showPaginaHome() {
		return "home";
	}

	@GetMapping("/")
	public String showPaginaHomeIndex() {
		return "home";
	}

	@GetMapping("/timeline")
	public String showPaginaTimeLine(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Usuario usuarioAtual = usuarioService.loadUserByUsername(username);

		List<Postagem> postagens = postagemService.obterPostagensDoUsuarioEAmigos(usuarioAtual.getId());

		model.addAttribute("postagens", postagens);
        List<Usuario> sugestoes = usuarioService.getSugestoesDeAmigos(usuarioAtual.getId());
        model.addAttribute("sugestoesAmizade", sugestoes);
		return "timeline";
	}

}
