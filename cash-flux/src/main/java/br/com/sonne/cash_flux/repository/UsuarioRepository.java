package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findUsuarioByNome(String nome);

}
