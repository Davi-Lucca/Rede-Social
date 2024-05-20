package br.com.unifacisa.lucas.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unifacisa.lucas.model.Usuario;
import br.com.unifacisa.lucas.repository.UsuarioRepository;

@Service
public class LoginService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public LoginService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Usuario usuario = this.usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return usuario;
	}

}
