package br.com.unifacisa.lucas.service;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.unifacisa.lucas.model.Usuario;
import br.com.unifacisa.lucas.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Usuario> getTodosUsuarios() {
		return this.usuarioRepository.findAll();
	}

	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}

	public Usuario getUsuarioById(Long id) throws UsernameNotFoundException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}

	public void adicionarAmigo(Usuario usuarioAtual, Usuario amigo) {
		usuarioAtual.getAmigos().add(amigo);
		amigo.getAmigos().add(usuarioAtual);

		this.usuarioRepository.save(usuarioAtual);
		this.usuarioRepository.save(amigo);
	}

	public List<Usuario> getSugestoesDeAmigos(Long userId) {
		return usuarioRepository.findSugestoesDeAmigos(userId);
	}


	public void createUser(String username, String password) throws BadRequestException {
		Optional<Usuario> usuarioExistente = this.usuarioRepository.findByUsername(username);
		if (usuarioExistente.isPresent()) {
			throw new RuntimeException("Nome de usuário já registrado.");
		}

		Usuario user = new Usuario();
		user.setUsername(username);
		user.setPassword(this.passwordEncoder.encode(password));
		this.usuarioRepository.save(user);

	}

}
