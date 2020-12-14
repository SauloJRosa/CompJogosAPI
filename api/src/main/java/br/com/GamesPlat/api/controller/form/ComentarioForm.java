package br.com.GamesPlat.api.controller.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.GamesPlat.api.models.Comentario;
import br.com.GamesPlat.api.models.Usuario;
import br.com.GamesPlat.api.repository.ComentarioRepository;

public class ComentarioForm {

	private Usuario autor;
	@NotNull
	@NotEmpty
	private String plataforma;
	@NotNull
	@NotEmpty
	private String jogo;
	@NotNull
	@NotEmpty
	private String comentario;
	@NotNull
	@Min(value=0)
	private double nota;
	
	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getJogo() {
		return jogo;
	}

	public void setJogo(String jogo) {
		this.jogo = jogo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public Comentario converter (ComentarioRepository comentarioRepository) {
		
		return new Comentario(this.autor, this.plataforma, this.jogo, this.comentario, this.nota);
	}
	
}
