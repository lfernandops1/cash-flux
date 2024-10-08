package br.com.sonne.cash_flux.repository;

import br.com.sonne.cash_flux.domain.Usuario;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de acesso a dados para a entidade {@link Usuario}.
 *
 * <p>Esta interface extende {@link JpaRepository}, fornecendo métodos prontos para operações CRUD
 * (Create, Read, Update, Delete) e consultas personalizadas utilizando JPA.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  /**
   * Busca um {@link Usuario} pelo seu email.
   *
   * @param email O email do usuário que será utilizado como critério de busca.
   * @return Um {@link Optional} contendo o usuário correspondente, se encontrado. Caso contrário,
   *     retorna um {@link Optional#empty()}.
   */
  Optional<Usuario> findByEmail(String email);

  /**
   * Busca um {@link Usuario} pelo seu telefone.
   *
   * @param telefone O número de telefone do usuário que será utilizado como critério de busca.
   * @return Um {@link Optional} contendo o usuário correspondente, se encontrado. Caso contrário,
   *     retorna um {@link Optional#empty()}.
   */
  Optional<Usuario> findByTelefone(String telefone);
}
