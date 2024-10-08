package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Folha;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolhaRepository extends JpaRepository<Folha, UUID> {

  Optional<Folha> findById(UUID uuid);

  List<Folha> findByUsuarioId(UUID uuid);

  List<Folha> findByTipoOrderByDataHoraAtualizacao(String tipo);
}
