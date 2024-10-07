package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoErroComMensagemComParseResource;
import static br.com.sonne.cash_flux.shared.util.SecurityUtil.obterLoginUsuarioLogado;
import static br.com.sonne.cash_flux.shared.util.SecurityUtil.obterUsuarioLogado;

import br.com.sonne.cash_flux.config.exception.ValidacaoException;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import br.com.sonne.cash_flux.shared.enums.EValidacao;
import br.com.sonne.cash_flux.shared.parse.UsuarioParse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired private UsuarioRepository usuarioRepository;

  private final PasswordEncoder passwordEncoder;

  public UsuarioServiceImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UsuarioResponseDTO criarUsuario(UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    validarSeEmailJaCadastrado(usuarioCadastroRequestDTO.getEmail());
    validarSeTelefoneJaCadastrado(usuarioCadastroRequestDTO.getTelefone());
    return executarComandoComTratamentoErroComMensagemComParseResource(
        () -> usuarioRepository.save(obterUsuario(usuarioCadastroRequestDTO)),
        "Erro",
        new UsuarioParse());
  }

  @Override
  public Usuario obterLoginUsuarioLogadoParaPesquisa() {
    return consultarPorEmail(obterLoginUsuarioLogado());
  }

  @Override
  public Usuario consultarPorEmail(String email) {
    return executarComandoComTratamentoErroComMensagem(
        () ->
            usuarioRepository
                .findByEmail(email)
                .orElseThrow(
                    () ->
                        new ValidacaoException(EValidacao.USUARIO_NAO_ENCONTRADO_POR_EMAIL, email)),
        "Erro ao consultar por email");
  }

  public Usuario carregarUsuarioDaSessao() {
    return obterUsuarioLogado();
  }

  private void validarSeEmailJaCadastrado(String email) {
    if (usuarioRepository.findByEmail(email).isPresent())
      throw new ValidacaoException(EValidacao.EMAIL_JA_CADASTRADO, email);
  }

  private void validarSeTelefoneJaCadastrado(String telefone) {
    if (usuarioRepository.findByTelefone(telefone).isPresent())
      throw new ValidacaoException(EValidacao.TELEFONE_JA_CADASTRADO, telefone);
  }

  private Usuario obterUsuario(UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    return new UsuarioParse()
        .toEntityComSenha(
            usuarioCadastroRequestDTO,
            passwordEncoder.encode(usuarioCadastroRequestDTO.getSenha()));
  }
}
