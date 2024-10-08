package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.Constantes.Mensagens.ERRO_AO_ATUALIZAR_DADOS_USUARIO;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.*;
import static br.com.sonne.cash_flux.shared.util.SecurityUtil.obterLoginUsuarioLogado;
import static br.com.sonne.cash_flux.shared.util.SecurityUtil.obterUsuarioLogado;
import static br.com.sonne.cash_flux.shared.util.ValidatorUsuarioUtil.validacaoFormatoEmailETelefone;
import static br.com.sonne.cash_flux.shared.util.ValidatorUsuarioUtil.validacaoFormatoEmailETelefoneParaAtualizar;

import br.com.sonne.cash_flux.config.exception.ValidacaoException;
import br.com.sonne.cash_flux.config.exception.ValidacaoNotFoundException;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import br.com.sonne.cash_flux.shared.enums.EValidacao;
import br.com.sonne.cash_flux.shared.parse.UsuarioParse;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;
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
  public Usuario consultarUsuarioPorId(UUID id) {
    return usuarioRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ValidacaoNotFoundException(
                    EValidacao.USUARIO_NAO_ENCONTRADO_POR_ID, id.toString()));
  }

  @Override
  public UsuarioResponseDTO criarUsuario(UsuarioCadastroRequestDTO usuario) {
    validacoesUsuario(usuario);
    return executarComandoComTratamentoErroComMensagemComParseResource(
        () -> usuarioRepository.save(obterUsuario(usuario)), "Erro", new UsuarioParse());
  }

  @Override
  public void alterarSenhaUsuario(String senha) {
    executarComandoComTratamentoSemRetorno(
        () -> {
          Usuario usuario = obterUsuarioLogado();
          usuario.setSenha(passwordEncoder.encode(senha));
          usuarioRepository.save(usuario);
        });
  }

  @Override
  public Usuario atualizar(Usuario usuario, UUID id) {
    validacaoFormatoEmailETelefoneParaAtualizar(usuario);
    return executarComandoComTratamentoErroComMensagem(
        () -> {
          Usuario usuarioAlterado = consultarUsuarioPorId(id);
          atualizarCamposNaoNulos(usuario, usuarioAlterado);
          usuarioAlterado.setDataHoraAtualizacao(LocalDateTime.now());

          return usuarioRepository.save(usuarioAlterado);
        },
        ERRO_AO_ATUALIZAR_DADOS_USUARIO);
  }

  private void atualizarCamposNaoNulos(Object origem, Object destino) {
    Field[] campos = origem.getClass().getDeclaredFields();

    for (Field campo : campos) {
      campo.setAccessible(true);
      try {
        Object valor = campo.get(origem);
        if (valor != null) {
          campo.set(destino, valor);
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Erro ao acessar campo: " + campo.getName(), e);
      }
    }
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

  private void validacoesUsuario(UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    validacaoFormatoEmailETelefone(usuarioCadastroRequestDTO);
    validarSeEmailJaCadastrado(usuarioCadastroRequestDTO.getEmail());
    validarSeTelefoneJaCadastrado(usuarioCadastroRequestDTO.getTelefone());
  }
}
