package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;

public interface UsuarioService {

  UsuarioResponseDTO criarUsuario(UsuarioCadastroRequestDTO usuarioRequestDTO);

  Usuario carregarUsuarioDaSessao();

  Usuario obterLoginUsuarioLogadoParaPesquisa();

  Usuario consultarPorEmail(String email);

  // Usuario obterUsuarioLogado();

  // Usuario consultarPorEmail(String email);

}
