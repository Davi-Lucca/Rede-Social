package br.com.unifacisa.lucas.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.unifacisa.lucas.dto.UsuarioDTO;
import br.com.unifacisa.lucas.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class RegistroController {
	
	private final UsuarioService usuarioService;
	
	public RegistroController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/registro")
	public String showPaginaRegistro() {
		return "registro";
	}
	
	@PostMapping("/registro")
	public String criarUsuario(@Valid @ModelAttribute("user") UsuarioDTO userDto) throws BadRequestException {
		this.usuarioService.createUser(userDto.username(), userDto.password());
		return "redirect:/login";
	}
}
