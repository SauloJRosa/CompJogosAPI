package br.com.GamesPlat.api.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ListaJogosDto {

	private String plataforma;
	private List<JogoDto> jogos = new ArrayList<>();

	public ListaJogosDto(){}
	
	public ListaJogosDto(String plataforma, List<JogoDto> jogos) {
		this.plataforma = plataforma;
		this.jogos = jogos;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public List<JogoDto> getJogos() {
		return jogos;
	}

	public void setJogos(List<JogoDto> jogos) {
		this.jogos = jogos;
	}

}
