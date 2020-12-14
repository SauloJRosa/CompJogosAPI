package br.com.GamesPlat.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.GamesPlat.api.models.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
	
	List<Optional<Comentario>> findByJogoAndPlataforma(String jogo, String plataforma);
	
}
