package br.com.GamesPlat.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.GamesPlat.api.models.Comentario;

public class ComentarioDto {

	private String autor;
	private String plataforma;
	private String jogo;
	private String comentario;
	private LocalDateTime dataCriacao;
	private double nota;

	public String getAutor() {
		return autor;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public String getJogo() {
		return jogo;
	}

	public String getComentario() {
		return comentario;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public double getNota() {
		return nota;
	}

	public ComentarioDto(Comentario comentario) {
		this.autor = comentario.getAutor().getNickname();
		this.plataforma = comentario.getPlataforma();
		this.jogo = comentario.getJogo();
		this.comentario = comentario.getComentario();
		this.dataCriacao = comentario.getDataCriacao();
		this.nota = comentario.getNota();
	}

	public ComentarioDto(Optional<Comentario> comentario){
		this.autor = comentario.get().getAutor().getNickname();
		this.plataforma = comentario.get().getPlataforma();
		this.jogo = comentario.get().getJogo();
		this.comentario = comentario.get().getComentario();
		this.dataCriacao = comentario.get().getDataCriacao();
		this.nota = comentario.get().getNota();
	}
	
	public ComentarioDto(){}
	
	public static List<ComentarioDto> converter(List<Optional<Comentario>> comentarios) {
	     return comentarios.stream().map(ComentarioDto::new).collect(Collectors.toList());
	    }
	
}
