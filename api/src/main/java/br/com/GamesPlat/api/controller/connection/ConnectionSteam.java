package br.com.GamesPlat.api.controller.connection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;

public class ConnectionSteam {

public ListaJogosDto obterJogos(String jogo) {
		
		String url = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
		JSONObject myresponse = new ConexaoAPIExterna().Conexao(url);
		
		ListaJogosDto steamJogos = new ListaJogosDto();
		steamJogos.setPlataforma("STEAM");
		
		List<String> ids = new ArrayList<>();
		
		try {
			
			for (int i = 0; i < myresponse.getJSONObject("applist").getJSONArray("apps").length(); i++) {
				String nome = myresponse.getJSONObject("applist").getJSONArray("apps").getJSONObject(i).getString("name");
				
				//(nome.toLowerCase().contains(jogo.toLowerCase()))
				
				if  (nome.toLowerCase().contains(jogo.toLowerCase())) {
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
