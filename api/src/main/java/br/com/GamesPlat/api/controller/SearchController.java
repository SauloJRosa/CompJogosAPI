package br.com.GamesPlat.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.GamesPlat.api.controller.connection.ConexaoAPIExterna;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;
import br.com.GamesPlat.api.controller.dto.ListaPlataformasDto;

@RestController
@RequestMapping("/search")
public class SearchController {

	@GetMapping
	public ResponseEntity<ListaPlataformasDto> pesquisarJogos(String jogo)
			throws URISyntaxException, IOException, InterruptedException {

		ListaPlataformasDto plataformas = new ListaPlataformasDto();
		
		ListaJogosDto gogJogos = new ConexaoAPIExterna().obterJogosGOG(jogo);
		plataformas.getPlataformas().add(gogJogos);
		
		ListaJogosDto steamJogos = new ConexaoAPIExterna().obterJogosSteam(jogo);
		plataformas.getPlataformas().add(steamJogos);
		
		return ResponseEntity.ok(plataformas);
	}


}
