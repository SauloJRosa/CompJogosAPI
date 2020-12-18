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
		precoFinalFormatado = precoFinalFormatado.replaceAll("[\\D]{3}", "");
		return Double.parseDouble(precoFinalFormatado);
	}
	
}
