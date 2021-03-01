package br.com.GamesPlat.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.GamesPlat.api.models.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

	Optional<Provider> findByProvider(String provider);
	
}
