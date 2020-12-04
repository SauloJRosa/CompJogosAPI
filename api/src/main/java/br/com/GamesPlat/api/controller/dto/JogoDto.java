package br.com.GamesPlat.api.controller.dto;

public class JogoDto {

	private String titulo;
	private String precoOriginal;
	private String desconto;
	private String precoFinal;

	public JogoDto(String titulo, String precoOriginal, String desconto, String precoFinal) {
		this.titulo = titulo;
		this.precoOriginal = precoOriginal;
		this.desconto = desconto + "%";
		this.precoFinal = precoFinal;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPrecoOrginal() {
		return precoOriginal;
	}

	public void setPrecoOrginal(String precoOrginal) {
		this.precoOriginal = precoOrginal;
	}

	public String getDesconto() {
		return desconto;
	}

	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}

	public String getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(String precoFinal) {
		this.precoFinal = precoFinal;
	}

}
