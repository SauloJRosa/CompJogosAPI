package br.com.GamesPlat.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.GamesPlat.api.controller.connection.ConnectionEpic;
import br.com.GamesPlat.api.controller.connection.ConnectionGog;
import br.com.GamesPlat.api.controller.connection.ConnectionSteam;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;
import br.com.GamesPlat.api.controller.dto.ListaPlataformasDto;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Cacheable(value = "AllPlatformsCache")
	@GetMapping
	public ResponseEntity<ListaPlataformasDto> pesquisarJogos(String jogo)
			throws URISyntaxException, IOException, InterruptedException {

		ListaPlataformasDto plataformas = new ListaPlataformasDto();
		
		plataformas.getPlataformas().add(pesquisarJogosGog(jogo).getBody());
		plataformas.getPlataformas().add(pesquisarJogosSteam(jogo).getBody());
		plataformas.getPlataformas().add(pesquisarJogosEpic(jogo).getBody());
		
		return ResponseEntity.ok(plataformas);
	}
	
	@GetMapping("/gog")
	public ResponseEntity<ListaJogosDto> pesquisarJogosGog(String jogo){
		return ResponseEntity.ok(new ConnectionGog().obterJogos(jogo));
	}

	@Cacheable(value = "SteamCache")
	@GetMapping("/steam")
	public ResponseEntity<ListaJogosDto> pesquisarJogosSteam(String jogo){
		return ResponseEntity.ok(new ConnectionSteam().obterJogos(jogo));
	}
	
	@GetMapping("/epic")
	public ResponseEntity<ListaJogosDto> pesquisarJogosEpic(String jogo){
		return ResponseEntity.ok(new ConnectionEpic().obterJogos(jogo));
	}
	
	@Scheduled(fixedRate = 3600000)
	@CacheEvict(value = { "SteamCache", "AllPlatformsCache" }, allEntries = true)
	public void evictAllcachesAtIntervals() {
	}

}
