package br.com.GamesPlat.api.controller.dto;

import java.util.Optional;

import br.com.GamesPlat.api.models.Usuario;

public class UsuarioDto {

	private String nickname;
	private String email;

	public UsuarioDto(Optional<Usuario> user) {
		this.nickname = user.get().getNickname();
		this.email = user.get().getEmail();
	}

	public UsuarioDto(Usuario user) {
		this.nickname = user.getNickname();
		this.email = user.getEmail();
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

}
