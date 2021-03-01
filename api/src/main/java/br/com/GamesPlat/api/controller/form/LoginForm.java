package br.com.GamesPlat.api.controller.form;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.GamesPlat.api.models.Provider;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.ProviderRepository;
import br.com.GamesPlat.api.repository.UsuarioRepository;

public class LoginForm {

	private String email;
	private String senha;
	private String provider;
	private String tokenProvider;
	private String nickname;
	
	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSenha() {
		return senha;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getTokenProvider() {
		return tokenProvider;
	}
	
	public void setTokenProvider(String tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(this.email, this.senha);
	}
	
	public Usuario converter(UsuarioRepository usuarioRepository) {

		return new Usuario(this.nickname, this.senha, this.email); 
	}

	public Usuario converter(UsuarioRepository usuarioRepository, ProviderRepository providerRepository) {

		Optional<Provider> prov = providerRepository.findByProvider(this.provider);
		return new Usuario(this.nickname, this.senha, this.email, prov.get()); 
	}
	
}
