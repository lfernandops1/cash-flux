package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Folha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FolhaRepository extends JpaRepository<Folha, UUID> {

    Optional<Folha> findById(UUID uuid);
}
