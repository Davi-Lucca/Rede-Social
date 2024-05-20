package br.com.unifacisa.lucas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.unifacisa.lucas.model.Postagem;
import br.com.unifacisa.lucas.model.Usuario;
import br.com.unifacisa.lucas.repository.PostagemRepository;
import br.com.unifacisa.lucas.repository.UsuarioRepository;

@Service
public class PostagemService {

	private final PostagemRepository postagemRepository;
	private final UsuarioRepository usuarioRepository;

	public PostagemService(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
		this.postagemRepository = postagemRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public List<Postagem> getAllPostagensPorUsuario(Long id) {
		return this.postagemRepository.findByUsuarioId(id);
	}
	
	public void apagarPostagem(Postagem postagem) {
		this.postagemRepository.delete(postagem);
	}
	
	public Postagem getPostagemById(Long id) {
		return this.postagemRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Erro ao recuperar postagem");
		});
	}

	public void postar(Long usuarioId, String conteudo, MultipartFile imagem) {
		try {
			Usuario usuario = new Usuario();
			usuario.setId(usuarioId);
			
	        byte[] imagemBytes = imagem.getBytes();


			Postagem postagem = new Postagem();
			postagem.setUser(usuario);
			postagem.setDataCriacao(LocalDateTime.now());
			postagem.setConteudo(conteudo);
			postagem.setImagem(imagemBytes);

			postagemRepository.save(postagem);
		}catch (Exception e) {
            throw new RuntimeException("Erro ao ler os bytes da imagem", e);
		}
		
	}

	public List<Postagem> obterPostagensDoUsuarioEAmigos(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		List<Postagem> postagens = usuario.getPostagens();

		for (Usuario amigo : usuario.getAmigos()) {
			postagens.addAll(amigo.getPostagens());
		}
		
		return postagens.stream()
		.sorted((obj1, obj2) -> obj2.getDataCriacao().compareTo(obj1.getDataCriacao())).toList();

		
	}

}
