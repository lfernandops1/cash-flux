package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Autenticacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutenticacaoRepository extends JpaRepository<Autenticacao, UUID> {

    Autenticacao findByUsuarioId(UUID usuarioId);

    Optional<Autenticacao> findByLogin(String email);

}
