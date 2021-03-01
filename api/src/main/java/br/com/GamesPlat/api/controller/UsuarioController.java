package br.com.GamesPlat.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.GamesPlat.api.config.security.AutenticacaoViaTokenFilter;
import br.com.GamesPlat.api.config.security.TokenService;
import br.com.GamesPlat.api.controller.dto.UsuarioDto;
import br.com.GamesPlat.api.controller.form.AtualizacaoUsuarioForm;
import br.com.GamesPlat.api.controller.form.UsuarioForm;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.ProviderRepository;
import br.com.GamesPlat.api.repository.UsuarioRepository;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ProviderRepository providerRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> cadastrar (@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder){
		
		if (form.getSenha() != null && form.getSenha() != "") {
			form.setSenha(new BCryptPasswordEncoder().encode(form.getSenha()));
		}
		
		Usuario usuario = form.converter(usuarioRepository, providerRepository);
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
	@GetMapping
	public ResponseEntity<UsuarioDto> exibirUsuario(HttpServletRequest request) {
		
		Long idUsuarioLogado = idUsuarioLogado(request);
		
		Optional<Usuario> user = usuarioRepository.findById(idUsuarioLogado);
		if(user.isPresent()) {
			return ResponseEntity.ok(new UsuarioDto(user));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<UsuarioDto> atualizar (HttpServletRequest request, @RequestBody @Valid AtualizacaoUsuarioForm form){
		
		Long idUsuarioLogado = idUsuarioLogado(request);
		form.setSenha(new BCryptPasswordEncoder().encode(form.getSenha()));
		
		Optional<Usuario> user = usuarioRepository.findById(idUsuarioLogado);
		if(user.isPresent()) {
			Usuario usuario = form.atualizar(idUsuarioLogado, usuarioRepository);
			return ResponseEntity.ok(new UsuarioDto(usuario));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> remover(HttpServletRequest request){
		
		Long idUsuarioLogado = idUsuarioLogado(request);
		
		Optional<Usuario> optional = usuarioRepository.findById(idUsuarioLogado);
		if(optional.isPresent()) {
			usuarioRepository.deleteById(idUsuarioLogado);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
	}

	private Long idUsuarioLogado(HttpServletRequest request) {
		AutenticacaoViaTokenFilter autenticacaoViaTokenFilter = new AutenticacaoViaTokenFilter(tokenService, usuarioRepository);
		String token = autenticacaoViaTokenFilter.recuperarToken(request);
		Long idUsuarioLogado = tokenService.getIdUsuario(token);
		return idUsuarioLogado;
	}
	
}
