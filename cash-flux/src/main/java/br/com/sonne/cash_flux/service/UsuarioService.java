package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import java.util.UUID;

public interface UsuarioService {

  /**
   * Cria um novo usuário.
   *
   * @param usuarioRequestDTO DTO contendo as informações do usuário a ser criado.
   * @return DTO com as informações do usuário criado.
   */
  UsuarioResponseDTO criarUsuario(UsuarioCadastroRequestDTO usuarioRequestDTO);

  /**
   * Obtém o usuário da sessão atual.
   *
   * @return o usuário logado na sessão.
   */
  Usuario carregarUsuarioDaSessao();

  /**
   * Consulta um usuário pelo email.
   *
   * @param email o email do usuário a ser consultado.
   * @return o usuário encontrado.
   */
  Usuario consultarPorEmail(String email);

  /**
   * Consulta um usuário pelo seu ID.
   *
   * @param uuid o ID do usuário a ser consultado.
   * @return o usuário encontrado.
   */
  Usuario consultarUsuarioPorId(UUID uuid);

  /**
   * Atualiza as informações de um usuário existente.
   *
   * @param usuario o usuário com as novas informações.
   * @param id o ID do usuário a ser atualizado.
   * @return o usuário atualizado.
   */
  Usuario atualizar(Usuario usuario, UUID id);

  /**
   * Altera a senha do usuário logado.
   *
   * @param senha a nova senha do usuário.
   */
  void alterarSenhaUsuario(String senha);
}
