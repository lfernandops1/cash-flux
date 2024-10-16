package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.CategoriaUsuario;
import br.com.sonne.cash_flux.domain.Folha;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriaUsuarioRepository
    extends JpaRepository<CategoriaUsuario, UUID>, JpaSpecificationExecutor<Folha> {}
