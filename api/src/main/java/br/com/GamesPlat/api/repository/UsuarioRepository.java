package br.com.GamesPlat.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.GamesPlat.api.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
