package br.com.GamesPlat.api.controller.dto;

import br.com.GamesPlat.api.models.Usuario;

public class UsuarioDto {
	
	private String username;
	private String email;
	
	public UsuarioDto(Usuario usuario) {
		this.username = usuario.getUsername();
		this.email = usuario.getEmail();
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

}
