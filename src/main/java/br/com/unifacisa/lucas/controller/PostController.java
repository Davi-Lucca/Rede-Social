package br.com.unifacisa.lucas.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.unifacisa.lucas.model.Postagem;
import br.com.unifacisa.lucas.model.Usuario;
import br.com.unifacisa.lucas.service.PostagemService;
import br.com.unifacisa.lucas.service.UsuarioService;

@Controller
public class PostController {

	private final UsuarioService usuarioService;
	private final PostagemService postagemService;

	public PostController(UsuarioService usuarioService, PostagemService postagemService) {
		this.usuarioService = usuarioService;
		this.postagemService = postagemService;
	}

	@GetMapping(value = "/imagem/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Long imageId) throws IOException {
		byte[] imageContent = this.postagemService.getPostagemById(imageId).getImagem();
		return new ResponseEntity<byte[]>(imageContent, HttpStatus.OK);
	}

	@PostMapping("/apagar/{id}")
	public String apagarPostagem(@PathVariable Long id, Authentication authentication) {
		Postagem postagem = postagemService.getPostagemById(id);

		if (postagem.getUsuario().getUsername().equals(authentication.getName())) {
			postagemService.apagarPostagem(postagem);

			return "redirect:/timeline";
		}

		return "redirect:/timeline";

	}

	@PostMapping("/postagem")
	public String fazerPostagem(@RequestParam("conteudo") String conteudo, MultipartFile imagem, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.loadUserByUsername(username);

		postagemService.postar(usuario.getId(), conteudo, imagem);

		return "redirect:/timeline";
	}

}
