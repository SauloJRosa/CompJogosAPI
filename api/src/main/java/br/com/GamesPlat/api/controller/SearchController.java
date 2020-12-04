package br.com.GamesPlat.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.GamesPlat.api.controller.connection.ConexaoAPIExterna;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;

@RestController
@RequestMapping("/search")
public class SearchController {

	@GetMapping
	public ResponseEntity<ListaJogosDto> pesquisarJogos(String jogo)
			throws URISyntaxException, IOException, InterruptedException {

		ListaJogosDto gogJogos = new ConexaoAPIExterna().obterJogosGOG(jogo);

		return ResponseEntity.ok(gogJogos);
	}


}
