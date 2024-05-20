package br.com.unifacisa.lucas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unifacisa.lucas.model.Postagem;

public interface PostagemRepository  extends JpaRepository<Postagem, Long> {
	
    List<Postagem> findByUsuarioId(Long id);


}
