package br.com.GamesPlat.api.controller.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;

public class ConexaoAPIExterna {
	
	private JSONObject Conexao(String url) {

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
	
	public ListaJogosDto obterJogosSteam(String jogo) {
		
		String url = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
		JSONObject myresponse = new ConexaoAPIExterna().Conexao(url);
		
		ListaJogosDto steamJogos = new ListaJogosDto();
		steamJogos.setPlataforma("STEAM");
		
		List<String> ids = new ArrayList<>();
		
		try {
			
			for (int i = 0; i < myresponse.getJSONObject("applist").getJSONArray("apps").length(); i++) {
				String nome = myresponse.getJSONObject("applist").getJSONArray("apps").getJSONObject(i).getString("name");
				
				if (nome.toLowerCase().contains(jogo.toLowerCase())) {
					ids.add(myresponse.getJSONObject("applist").getJSONArray("apps").getJSONObject(i).getString("appid"));
				}
			}
						
			for (int i = 0; i < ids.size(); i++) {
				
				String url2 = "https://store.steampowered.com/api/appdetails?appids="+ids.get(i);
				JSONObject myresponse2 = new ConexaoAPIExterna().Conexao(url2);
				
				try {
					if(myresponse2.getJSONObject(String.format("%s", ids.get(i))).getString("success").equals("false")) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}
				
				
				if(myresponse2.getJSONObject(String.format("%s", ids.get(i))).getJSONObject("data").getString("is_free").equals("true")) {
					
					String precoOriginal = "0";
					String desconto = "0";
					String precoFinal= "0";
					
					JogoDto game = new JogoDto(
							myresponse2.getJSONObject(String.format("%s", ids.get(i))).getJSONObject("data").getString("name"),
							precoOriginal,
							desconto,
							precoFinal
							);
					steamJogos.getJogos().add(game);
					
				}else {
					try {
						JogoDto game = new JogoDto(
								myresponse2.getJSONObject(String.format("%s", ids.get(i))).getJSONObject("data").getString("name"),
								myresponse2.getJSONObject(String.format("%s", ids.get(i))).getJSONObject("data").getJSONObject("price_overview").getString("initial_formatted"),
								myresponse2.getJSONObject(String.format("%s", ids.get(i))).getJSONObject("data").getJSONObject("price_overview").getString("discount_percent"),
								myresponse2.getJSONObject(String.format("%s", ids.get(i))).getJSONObject("data").getJSONObject("price_overview").getString("final_formatted")
								);
						steamJogos.getJogos().add(game);
					} catch (Exception e) {
						continue;
					}
					
				}	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return steamJogos;
	}
}
