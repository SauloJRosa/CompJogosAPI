package br.com.GamesPlat.api.controller.dto;

public class JogoDto {

	private String titulo;
	private String precoOriginal;
	private String desconto;
	private String precoFinal;
	private String plataforma;
	private String thumbnail;
	private String url;

	public JogoDto(String titulo, String precoOriginal, String desconto, String precoFinal, String thumbnail,
			String url, String plataforma) {
		this.titulo = titulo;
		this.precoOriginal = precoOriginal;
		this.desconto = desconto + "%";
		this.precoFinal = precoFinal;
		this.thumbnail = thumbnail;
		this.plataforma = plataforma;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPrecoOriginal() {
		return precoOriginal;
	}

	public void setPrecoOriginal(String precoOriginal) {
		this.precoOriginal = precoOriginal;
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

	public Double converterPrecoEmDouble() {

		String precoFinalFormatado = this.precoFinal.replace(",", ".");
		precoFinalFormatado = precoFinalFormatado.replaceAll("[\\D]{1}", "");
		return Double.parseDouble(precoFinalFormatado);
	}

}
