package br.com.sonne.cash_flux.shared.parse;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import br.com.sonne.cash_flux.shared.enums.Role;
import br.com.sonne.cash_flux.shared.sample.Parse;
import java.time.LocalDateTime;

public class UsuarioParse implements Parse<UsuarioRequestDTO, Usuario, UsuarioResponseDTO> {
  @Override
  public Usuario toEntity(UsuarioRequestDTO requestDTO) {
    Usuario usuario = new Usuario();
    usuario.setSenha(requestDTO.getSenha());
    return usuario;
  }

  public Usuario usuarioCadastroRequestDTOToEntity(UsuarioCadastroRequestDTO resourceDTO) {
    Usuario usuario = new Usuario();
    usuario.setNome(resourceDTO.getNome());
    usuario.setSobrenome(resourceDTO.getSobrenome());
    usuario.setEmail(resourceDTO.getEmail());
    usuario.setSenha(resourceDTO.getSenha());
    usuario.setTelefone(resourceDTO.getTelefone());
    usuario.setAtivo(resourceDTO.isAtivo());
    usuario.setDataHoraAtualizacao(LocalDateTime.now());
    usuario.setDataHoraCriacao(LocalDateTime.now());
    usuario.setUserRole(Role.USER);
    usuario.setAtivo(true);
    return usuario;
  }

  @Override
  public UsuarioResponseDTO toResponse(Usuario usuario) {
    UsuarioResponseDTO response = new UsuarioResponseDTO();
    response.setId(usuario.getId());
    response.setNome(usuario.getNome());
    response.setSobrenome(usuario.getSobrenome());
    response.setEmail(usuario.getEmail());
    response.setTelefone(usuario.getTelefone());
    response.setAtivo(usuario.getAtivo());
    return response;
  }

  private Usuario retornarUsuario(UsuarioResponseDTO usuarioResponseDTO) {
    Usuario usuario = new Usuario();
    usuario.setId(usuarioResponseDTO.getId());
    return usuario;
  }

  public Usuario toEntityComSenha(
      UsuarioCadastroRequestDTO usuarioCadastroRequestDTO, String senhaCriptografada) {
    Usuario usuario = usuarioCadastroRequestDTOToEntity(usuarioCadastroRequestDTO);
    usuario.setSenha(senhaCriptografada);
    return usuario;
  }
}
