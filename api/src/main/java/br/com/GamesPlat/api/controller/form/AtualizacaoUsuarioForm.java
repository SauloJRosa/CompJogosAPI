package br.com.GamesPlat.api.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.UsuarioRepository;

public class AtualizacaoUsuarioForm {

	@NotNull
	@NotEmpty
	private String nickname;
	@NotNull
	@NotEmpty
	private String senha;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String username) {
		this.nickname = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String password) {
		this.senha = password;
	}

	public Usuario atualizar(Long id, UsuarioRepository usuarioRepository) {
		Optional<Usuario> user = usuarioRepository.findById(id);
		user.get().setNickname(this.nickname);
		user.get().setSenha(this.senha);
		
		return user.get();
	}
	
}
