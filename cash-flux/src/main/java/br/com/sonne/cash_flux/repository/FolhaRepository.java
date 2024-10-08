package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Folha;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FolhaRepository
    extends JpaRepository<Folha, UUID>, JpaSpecificationExecutor<Folha> {

  Optional<Folha> findById(UUID uuid);

  List<Folha> findByUsuarioId(UUID uuid);

  @Query(
      "SELECT f FROM Folha f WHERE f.usuario.id = :usuarioId AND f.dataHoraExclusao IS NULL "
          + "ORDER BY CASE WHEN f.dataHoraAtualizacao IS NULL THEN f.dataHoraCriacao ELSE f.dataHoraAtualizacao END ASC")
  List<Folha> findByUsuarioIdAndDataHoraExclusaoIsNull(@Param("usuarioId") UUID usuarioId);

  List<Folha> findByTipoOrderByDataHoraAtualizacao(String tipo);
}
