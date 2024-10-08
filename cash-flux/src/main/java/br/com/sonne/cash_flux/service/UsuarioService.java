package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import java.util.UUID;

public interface UsuarioService {

  UsuarioResponseDTO criarUsuario(UsuarioCadastroRequestDTO usuarioRequestDTO);

  Usuario carregarUsuarioDaSessao();

  Usuario obterLoginUsuarioLogadoParaPesquisa();

  Usuario consultarPorEmail(String email);

  Usuario consultarUsuarioPorId(UUID uuid);

  Usuario atualizar(Usuario usuario, UUID id);

  void alterarSenhaUsuario(String senha);
}
