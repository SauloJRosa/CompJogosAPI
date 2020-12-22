package br.com.GamesPlat.api.controller.connection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.ordenacao.Ordenacao;

public class ConnectionGog {
	
	public List<JogoDto> obterJogos(String jogo, String hide, String sort) {
		String jogoedit = jogo.replaceAll(" ", "+");
		String urljogo = "https://embed.gog.com/games/ajax/filtered?mediaType=game&search=" + jogoedit + "&limit=" + 30 + "&page=" + 1 + "&hide=" + hide + "&sort=popularity";
		JSONObject myresponse = new ConexaoAPIExterna().Conexao(urljogo);

		List<JogoDto> gogJogos = new ArrayList<>();
		
		try {

			for (int i = 0; i < myresponse.getJSONArray("products").length(); i++) {
				JogoDto game = new JogoDto(
						myresponse.getJSONArray("products").getJSONObject(i).getString("title"),
						myresponse.getJSONArray("products").getJSONObject(i).getJSONObject("price").getString("baseAmount"),
						myresponse.getJSONArray("products").getJSONObject(i).getJSONObject("price").getString("discountPercentage"),
						myresponse.getJSONArray("products").getJSONObject(i).getJSONObject("price").getString("finalAmount"),
						"http://" + myresponse.getJSONArray("products").getJSONObject(i).getString("image") + "_392.jpg",
						"http://gog.com/" + myresponse.getJSONArray("products").getJSONObject(i).getString("url"),
						"GOG"
						);
				gogJogos.add(game);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return Ordenacao.ordenarPorPreco(sort, gogJogos);
	}

	

}
