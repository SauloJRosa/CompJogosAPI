package br.com.GamesPlat.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.GamesPlat.api.config.security.AutenticacaoViaTokenFilter;
import br.com.GamesPlat.api.config.security.TokenService;
import br.com.GamesPlat.api.controller.dto.ComentarioDto;
import br.com.GamesPlat.api.controller.form.ComentarioForm;
import br.com.GamesPlat.api.models.Comentario;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.ComentarioRepository;
import br.com.GamesPlat.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@GetMapping
	public ResponseEntity<List<ComentarioDto>> exibirComentarios(String plataforma, String jogo){
		
		List<Optional<Comentario>> listaComentarios = comentarioRepository.findByJogoAndPlataforma(jogo, plataforma);
		
		new ComentarioDto();
		List<ComentarioDto> listaComentariosDto = ComentarioDto.converter(listaComentarios);
		
		return ResponseEntity.ok().body(listaComentariosDto);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<ComentarioDto> adicionarComentario(@RequestBody @Valid ComentarioForm form, UriComponentsBuilder uriBuilder, HttpServletRequest request){
		
		Optional<Usuario> usuario = usuarioRepository.findById(idUsuarioLogado(request));
		form.setAutor(usuario.get());
		Comentario comentario = form.converter(comentarioRepository);
		comentarioRepository.save(comentario);
		
		//URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.ok().body(new ComentarioDto(comentario));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> removerComentario(HttpServletRequest request, @PathVariable Long id){
		
		Long idUsuarioLogado = idUsuarioLogado(request);
		
		Optional<Comentario> comentario = comentarioRepository.findByIdAndAutorId(id, idUsuarioLogado);
		if(comentario.isPresent()) {
			comentarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
		
	}
	
	
	private Long idUsuarioLogado(HttpServletRequest request) {
		AutenticacaoViaTokenFilter autenticacaoViaTokenFilter = new AutenticacaoViaTokenFilter(tokenService, usuarioRepository);
		String token = autenticacaoViaTokenFilter.recuperarToken(request);
		Long idUsuarioLogado = tokenService.getIdUsuario(token);
		return idUsuarioLogado;
	}
	
}
