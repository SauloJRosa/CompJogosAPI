package br.com.GamesPlat.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
import br.com.GamesPlat.api.controller.ordenacao.Ordenacao;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Cacheable(value = "AllPlatformsCache")
	@GetMapping
	public ResponseEntity<Page<JogoDto>> pesquisarJogos(String jogo,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "6") Integer size,
			@RequestParam(defaultValue = "") String hide,
			@RequestParam(defaultValue = "") String sort)
			throws URISyntaxException, IOException, InterruptedException {

		List<JogoDto> jogos = new ArrayList<>();
		
		jogos.addAll(new ConnectionGog().obterJogos(jogo, hide, sort)); 
		jogos.addAll(new ConnectionSteam().obterJogos(jogo, hide, sort));
		jogos.addAll(new ConnectionEpic().obterJogos(jogo, sort));
		
		Ordenacao.ordenarPorPreco(sort, jogos);
		
		return paginacao(page, size, jogos);
		
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
	
	private ResponseEntity<Page<JogoDto>> paginacao(Integer page, Integer size, List<JogoDto> listaJogosDto) {
		PagedListHolder<JogoDto> pageHolder = new PagedListHolder<JogoDto>(listaJogosDto);
		pageHolder.setPageSize(size); 
		pageHolder.setPage(page);      
		
		Pageable pageable = PageRequest.of(page, size);
		Page<JogoDto> pageNum = new PageImpl<JogoDto>(pageHolder.getPageList(), pageable, listaJogosDto.size());
		
		return ResponseEntity.ok(pageNum);
	}

}
