package br.com.GamesPlat.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.GamesPlat.api.controller.connection.ConnectionEpic;
import br.com.GamesPlat.api.controller.connection.ConnectionGog;
import br.com.GamesPlat.api.controller.connection.ConnectionSteam;
import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;
import br.com.GamesPlat.api.controller.dto.ListaPlataformasDto;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Cacheable(value = "AllPlatformsCache")
	@GetMapping
	public ResponseEntity<Page<ListaJogosDto>> pesquisarJogos(String jogo,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "6") Integer size,
			@RequestParam(defaultValue = "") String hide,
			@RequestParam(defaultValue = "") String sort)
			throws URISyntaxException, IOException, InterruptedException {

		ListaPlataformasDto plataformas = new ListaPlataformasDto();
		
		plataformas.getPlataformas().add(new ListaJogosDto("GOG", pesquisarJogosGog(jogo, page, size, hide, sort).getBody().getContent()));
		
		plataformas.getPlataformas().add(new ListaJogosDto("STEAM", pesquisarJogosSteam(jogo, page, size, hide, sort).getBody().getContent()));
		
		plataformas.getPlataformas().add(new ListaJogosDto("Epic Games", pesquisarJogosEpic(jogo, page, size, sort).getBody().getContent())); 
		
		int total = 0;
		for (int i = 0; i < plataformas.getPlataformas().size(); i++) {
			total += plataformas.getPlataformas().get(i).getJogos().size();
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ListaJogosDto> pageNum = new PageImpl<ListaJogosDto>(plataformas.getPlataformas(), pageable, total);
		
		return ResponseEntity.ok(pageNum);
	}
	
	@GetMapping("/gog")
	public ResponseEntity<Page<JogoDto>> pesquisarJogosGog(String jogo,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "") String hide,
			@RequestParam(defaultValue = "") String sort){
		
		return paginacao(page, size, new ConnectionGog().obterJogos(jogo, hide, sort));
	}
	
	@GetMapping("/epic")
	public ResponseEntity<Page<JogoDto>> pesquisarJogosEpic(String jogo,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "") String sort){
		
		return paginacao(page, size, new ConnectionEpic().obterJogos(jogo, sort));
	}
	
	@Cacheable(value = "SteamCache")
	@GetMapping("/steam")
	public ResponseEntity<Page<JogoDto>> pesquisarJogosSteam(String jogo,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "") String hide,
			@RequestParam(defaultValue = "") String sort){
		
		return paginacao(page, size, new ConnectionSteam().obterJogos(jogo, hide ,sort));
	}
	
	@Scheduled(fixedRate = 3600000)
	@CacheEvict(value = { "SteamCache", "AllPlatformsCache" }, allEntries = true)
	public void evictAllcachesAtIntervals() {
	}
	
	private ResponseEntity<Page<JogoDto>> paginacao(Integer page, Integer size, ListaJogosDto listaJogosDto) {
		PagedListHolder<JogoDto> pageHolder = new PagedListHolder<JogoDto>(listaJogosDto.getJogos());
		pageHolder.setPageSize(size); 
		pageHolder.setPage(page);      
		
		Pageable pageable = PageRequest.of(page, size);
		Page<JogoDto> pageNum = new PageImpl<JogoDto>(pageHolder.getPageList(), pageable, listaJogosDto.getJogos().size());
		
		return ResponseEntity.ok(pageNum);
	}

}
