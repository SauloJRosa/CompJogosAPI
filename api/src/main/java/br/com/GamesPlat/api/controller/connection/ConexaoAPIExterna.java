package br.com.GamesPlat.api.controller.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;

public class ConexaoAPIExterna {
	
	public JSONObject Conexao(String url) {

		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject myresponse = new JSONObject(response.toString());

			return myresponse;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
	
	public ListaJogosDto obterJogosGOG(String jogo) {
		String jogoedit = jogo.replaceAll(" ", "+");
		String urljogo = "https://embed.gog.com/games/ajax/filtered?mediaType=game&search=" + jogoedit;
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
		return gogJogos;
	}
}
