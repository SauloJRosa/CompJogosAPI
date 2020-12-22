package br.com.GamesPlat.api.controller.ordenacao;

import java.util.List;

import br.com.GamesPlat.api.controller.dto.JogoDto;

public class Ordenacao {

	public static List<JogoDto> ordenarPorPreco(String sort, List<JogoDto> jogos) {
		if(sort.equals("price.ASC")) {
			jogos.sort((j1, j2) -> Double.compare(j1.converterPrecoEmDouble(), j2.converterPrecoEmDouble()));
		} else {
			jogos.sort((j1, j2) -> (-1)*Double.compare(j1.converterPrecoEmDouble(), j2.converterPrecoEmDouble()));
		}
		return jogos;
	}
	
}
