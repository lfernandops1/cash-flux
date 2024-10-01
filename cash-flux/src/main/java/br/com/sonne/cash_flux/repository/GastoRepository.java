package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.domain.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, UUID> {
    List<Gasto> findByMes(Mes mes);

    List<Gasto> findByUsuario_Id(UUID usuarioId);
}
