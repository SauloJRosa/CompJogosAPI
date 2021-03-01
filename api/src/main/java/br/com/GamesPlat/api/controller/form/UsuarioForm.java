package br.com.GamesPlat.api.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.GamesPlat.api.models.Provider;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.ProviderRepository;
import br.com.GamesPlat.api.repository.UsuarioRepository;

public class UsuarioForm {

	@NotNull
	@NotEmpty
	private String nickname;
	
	private String senha;
	
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String provider;

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
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Usuario converter(UsuarioRepository usuarioRepository, ProviderRepository providerRepository) {

		Optional<Provider> prov = providerRepository.findByProvider(this.provider);
		return new Usuario(this.nickname, this.senha, this.email, prov.get());
	}

	public Usuario converter(UsuarioRepository usuarioRepository) {
		return new Usuario(this.nickname, this.senha, this.email);
	}
}
