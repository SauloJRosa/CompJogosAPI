package br.com.GamesPlat.api.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ListaPlataformasDto {

	private List<ListaJogosDto> plataformas = new ArrayList<>();

	public List<ListaJogosDto> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(List<ListaJogosDto> plataformas) {
		this.plataformas = plataformas;
	}
	
}
