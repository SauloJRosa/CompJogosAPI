package br.com.GamesPlat.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.GamesPlat.api.controller.dto.UsuarioDto;
import br.com.GamesPlat.api.controller.form.UsuarioForm;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> cadastrar (@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder){
		
		Usuario usuario = form.converter(usuarioRepository);
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if(optional.isPresent()) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
	}
	
}
