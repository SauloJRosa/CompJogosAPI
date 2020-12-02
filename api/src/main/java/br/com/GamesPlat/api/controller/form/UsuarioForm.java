package br.com.GamesPlat.api.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.UsuarioRepository;

public class UsuarioForm {

	@NotNull
	@NotEmpty
	private String nickname;
	@NotNull
	@NotEmpty
	private String senha;
	@NotNull
	@NotEmpty
	private String email;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario converter(UsuarioRepository usuarioRepository) {

		return new Usuario(this.nickname, this.senha, this.email);
	}

}
