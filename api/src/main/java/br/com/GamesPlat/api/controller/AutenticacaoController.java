package br.com.GamesPlat.api.controller;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.GamesPlat.api.config.security.TokenService;
import br.com.GamesPlat.api.controller.connection.ConexaoAPIExterna;
import br.com.GamesPlat.api.controller.dto.TokenDto;
import br.com.GamesPlat.api.controller.form.LoginForm;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.ProviderRepository;
import br.com.GamesPlat.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Value("${api.facebook.id}")
	private String appId;
	@Value("${api.facebook.secret}")
	private String appSecret;
	@Value("${facebook.pass}")
	private String facePass;
	
	@Value("${google.pass}")
	private String googlePass;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ProviderRepository providerRepository;
	
	@PostMapping	
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) throws JSONException {
		
			
		if (form.getProvider().equals("FACEBOOK")) {
			return autenticaFacebook(form);
		}
		
		if (form.getProvider().equals("GOOGLE")) {
			return autenticaGoogle(form);
		}
		
		if (form.getProvider().equals("NATIVO")) {
			return autentica(form);
		}
			
		return ResponseEntity.badRequest().build();
		
	}

	private ResponseEntity<TokenDto> autentica(LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
			
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
				
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
				
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	private ResponseEntity<TokenDto> autenticaFacebook(LoginForm form) throws JSONException {
		if (autenticaTokenFacebook(form.getTokenProvider())) {
			
			if (usuarioRepository.findByEmail(form.getEmail()).isEmpty()) {
				form.setSenha(facePass);
				form.setSenha(new BCryptPasswordEncoder().encode(form.getSenha()));
				Usuario usuario = form.converter(usuarioRepository, providerRepository);
				usuarioRepository.save(usuario);
			}
			
			form.setSenha(facePass);
			return autentica(form);
			
		} else {
			return ResponseEntity.badRequest().build();
			
		}
	}
	
	private boolean autenticaTokenFacebook(String tokenFacebook) throws JSONException {
		
		String urlAcessToken = "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&client_secret=" + appSecret + "&grant_type=client_credentials";
		JSONObject myresponsewithtoken = new ConexaoAPIExterna().Conexao(urlAcessToken);
		String acessToken = myresponsewithtoken.getString("access_token");
		
		String urlValidation = "https://graph.facebook.com/debug_token?input_token="+ tokenFacebook + "&access_token=" + acessToken;
		JSONObject myvalidationresponse = new ConexaoAPIExterna().Conexao(urlValidation);
		
		String validation = myvalidationresponse.getJSONObject("data").getString("is_valid");
		
		if (validation.equals("true")) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private ResponseEntity<TokenDto> autenticaGoogle(LoginForm form) throws JSONException {
		if (autenticaTokenGoogle(form.getTokenProvider())) {
			
			if (usuarioRepository.findByEmail(form.getEmail()).isEmpty()) {
				form.setSenha(googlePass);
				form.setSenha(new BCryptPasswordEncoder().encode(form.getSenha()));
				Usuario usuario = form.converter(usuarioRepository, providerRepository);
				usuarioRepository.save(usuario);
			}
			
			form.setSenha(googlePass);
			return autentica(form);
			
		} else {
			return ResponseEntity.badRequest().build();
			
		}
	}
	
	private boolean autenticaTokenGoogle(String tokenGoogle) throws JSONException {
		
		String urlValidation = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + tokenGoogle;
		JSONObject myvalidationresponse = new ConexaoAPIExterna().Conexao(urlValidation);
		
		String validation = myvalidationresponse.getString("verified_email");
		
		if (validation.equals("true")) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
