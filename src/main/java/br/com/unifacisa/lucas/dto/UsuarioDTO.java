package br.com.unifacisa.lucas.dto;
import jakarta.validation.constraints.NotEmpty;

public record UsuarioDTO(String _csrf, @NotEmpty(message = "Usuário não pode ser vazio") String username, @NotEmpty String password) {

}
