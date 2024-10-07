package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Gasto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, UUID> {

  List<Gasto> findByUsuarioId(UUID usuarioId);
}
