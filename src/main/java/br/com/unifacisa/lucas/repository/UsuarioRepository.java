package br.com.unifacisa.lucas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unifacisa.lucas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);

	@Query("SELECT u FROM Usuario u WHERE u.id <> :userId AND u NOT IN (SELECT a FROM Usuario usuario JOIN usuario.amigos a WHERE usuario.id = :userId)")
	List<Usuario> findSugestoesDeAmigos(@Param("userId") Long userId);

}
