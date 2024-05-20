package br.com.unifacisa.lucas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.unifacisa.lucas.repository.UsuarioRepository;

@Controller
public class LoginController {
	
	@Autowired
	UsuarioRepository repository;

	@GetMapping("/login")
	public String showPaginaLogin() {
		return "login";
	}
	
	
}
