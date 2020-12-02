package br.com.GamesPlat.api.controller.dto;

import java.util.Optional;

import br.com.GamesPlat.api.models.Usuario;

public class UsuarioDto {
	
	private String login;
	private String username;
	private String email;
	
	public UsuarioDto(Optional<Usuario> user) {
		this.login = user.get().getLogin();
		this.username = user.get().getUsername();
		this.email = user.get().getEmail();
	}

	public UsuarioDto(Usuario user) {
		this.login = user.getLogin();
		this.username = user.getUsername();
		this.email = user.getEmail();
	}

	
	public String getLogin() {
		return login;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

}
