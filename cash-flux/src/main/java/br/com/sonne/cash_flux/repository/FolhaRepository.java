package br.com.sonne.cash_flux.repository;

import static br.com.sonne.cash_flux.shared.Constantes.Queries.QUERY_PROCURAR_POR_ID_USUARIO_E_DATA_HORA_EXCLUSAO_NULA;

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

  @Query(QUERY_PROCURAR_POR_ID_USUARIO_E_DATA_HORA_EXCLUSAO_NULA)
  List<Folha> findByUsuarioIdAndDataHoraExclusaoIsNull(@Param("usuarioId") UUID usuarioId);
}
