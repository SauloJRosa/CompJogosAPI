package br.com.GamesPlat.api.controller.connection;

import org.json.JSONObject;

import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;

public class ConnectionGog {
	
	public ListaJogosDto obterJogos(String jogo, String hide, String sort) {
		String jogoedit = jogo.replaceAll(" ", "+");
		String urljogo = "https://embed.gog.com/games/ajax/filtered?mediaType=game&search=" + jogoedit + "&limit=" + 30 + "&page=" + 1 + "&hide=" + hide + "&sort=popularity";
		JSONObject myresponse = new ConexaoAPIExterna().Conexao(urljogo);

		ListaJogosDto gogJogos = new ListaJogosDto();
		gogJogos.setPlataforma("GOG");
		
		try {

			for (int i = 0; i < myresponse.getJSONArray("products").length(); i++) {
				JogoDto game = new JogoDto(
						myresponse.getJSONArray("products").getJSONObject(i).getString("title"),
						myresponse.getJSONArray("products").getJSONObject(i).getJSONObject("price").getString("baseAmount"),
						myresponse.getJSONArray("products").getJSONObject(i).getJSONObject("price").getString("discountPercentage"),
						myresponse.getJSONArray("products").getJSONObject(i).getJSONObject("price").getString("finalAmount")
						);
				gogJogos.getJogos().add(game);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if(sort.equals("price")) {
			gogJogos.getJogos().sort((j1, j2) -> Double.compare(j1.converterPrecoEmDouble(), j2.converterPrecoEmDouble()));
		}
		
		return gogJogos;
	}

}
