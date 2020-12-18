package br.com.GamesPlat.api.controller.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.GamesPlat.api.controller.dto.JogoDto;
import br.com.GamesPlat.api.controller.dto.ListaJogosDto;

public class ConnectionEpic {

	public ListaJogosDto obterJogos(String jogo, String sort) {
		
		ListaJogosDto epicJogos = new ListaJogosDto();
		epicJogos.setPlataforma("Epic Games");
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();

			Map<String, Object> epicRequestVariables = new HashMap<String, Object>();
			epicRequestVariables.put("category", "games/edition/base|bundles/games|editors");
			epicRequestVariables.put("count", 30);
			epicRequestVariables.put("country", "BR");
			epicRequestVariables.put("keywords", jogo);
			epicRequestVariables.put("locale", "pt-BR");
			epicRequestVariables.put("sortDir", "DESC");
			epicRequestVariables.put("allowCountries", "BR");
			epicRequestVariables.put("start", 0);
			epicRequestVariables.put("tag", "");
			epicRequestVariables.put("withPrice", true);
			
			Map<String, Object> epicRequest = new HashMap<String, Object>();
	        epicRequest.put("query", "query searchStoreQuery($allowCountries: String, $category: String, $count: Int, $country: String!, $keywords: String, $locale: String, $namespace: String, $itemNs: String, $sortBy: String, $sortDir: String, $start: Int, $tag: String, $releaseDate: String, $withPrice: Boolean = false, $withPromotions: Boolean = false, $priceRange: String, $freeGame: Boolean, $onSale: Boolean, $effectiveDate: String) {\n  Catalog {\n    searchStore(allowCountries: $allowCountries, category: $category, count: $count, country: $country, keywords: $keywords, locale: $locale, namespace: $namespace, itemNs: $itemNs, sortBy: $sortBy, sortDir: $sortDir, releaseDate: $releaseDate, start: $start, tag: $tag, priceRange: $priceRange, freeGame: $freeGame, onSale: $onSale, effectiveDate: $effectiveDate) {\n      elements {\n        title\n        id\n        namespace\n        description\n        effectiveDate\n        keyImages {\n          type\n          url\n        }\n        currentPrice\n        seller {\n          id\n          name\n        }\n        productSlug\n        urlSlug\n        url\n        tags {\n          id\n        }\n        items {\n          id\n          namespace\n        }\n        customAttributes {\n          key\n          value\n        }\n        categories {\n          path\n        }\n        price(country: $country) @include(if: $withPrice) {\n          totalPrice {\n            discountPrice\n            originalPrice\n            voucherDiscount\n            discount\n            currencyCode\n            currencyInfo {\n              decimals\n            }\n            fmtPrice(locale: $locale) {\n              originalPrice\n              discountPrice\n              intermediatePrice\n            }\n          }\n          lineOffers {\n            appliedRules {\n              id\n              endDate\n              discountSetting {\n                discountType\n              }\n            }\n          }\n        }\n        promotions(category: $category) @include(if: $withPromotions) {\n          promotionalOffers {\n            promotionalOffers {\n              startDate\n              endDate\n              discountSetting {\n                discountType\n                discountPercentage\n              }\n            }\n          }\n          upcomingPromotionalOffers {\n            promotionalOffers {\n              startDate\n              endDate\n              discountSetting {\n                discountType\n                discountPercentage\n              }\n            }\n          }\n        }\n      }\n      paging {\n        count\n        total\n      }\n    }\n  }\n}\n");

	        epicRequest.put("variables", epicRequestVariables);
	        
			String body = mapper.writeValueAsString(epicRequest);
			String url = "https://www.epicgames.com/graphql";
			
			JSONObject myresponse = new ConexaoAPIExterna().ConexaoPost(url, body);
			
			int max = myresponse.getJSONObject("data").getJSONObject("Catalog").getJSONObject("searchStore").getJSONArray("elements").length();
			for (int i = 0; i < max ; i++) {
				JogoDto game = new JogoDto(
						myresponse.getJSONObject("data").getJSONObject("Catalog").getJSONObject("searchStore").getJSONArray("elements").getJSONObject(i).getString("title"),
						myresponse.getJSONObject("data").getJSONObject("Catalog").getJSONObject("searchStore").getJSONArray("elements").getJSONObject(i).getJSONObject("price").getJSONObject("totalPrice").getJSONObject("fmtPrice").getString("originalPrice"),
						myresponse.getJSONObject("data").getJSONObject("Catalog").getJSONObject("searchStore").getJSONArray("elements").getJSONObject(i).getJSONObject("price").getJSONObject("totalPrice").getString("discount"),
						myresponse.getJSONObject("data").getJSONObject("Catalog").getJSONObject("searchStore").getJSONArray("elements").getJSONObject(i).getJSONObject("price").getJSONObject("totalPrice").getJSONObject("fmtPrice").getString("discountPrice")
						);
				epicJogos.getJogos().add(game);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if(sort.equals("price")) {
			epicJogos.getJogos().sort((j1, j2) -> Double.compare(j1.converterPrecoEmDouble(), j2.converterPrecoEmDouble()));
		}
		
		return epicJogos;
		
	}
	
}
