package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.Constantes.Mensagens.*;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.*;
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

/**
 * Implementação do serviço para gerenciamento de usuários.
 *
 * <p>A classe implementa a interface {@link UsuarioService} e fornece funcionalidades para criação,
 * atualização, consulta e manipulação de dados do usuário, além de operações de validação e
 * alteração de senha.
 *
 * <p>As operações de persistência utilizam o repositório {@link UsuarioRepository} e a criptografia
 * de senhas é tratada pela implementação de {@link PasswordEncoder}.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired private UsuarioRepository usuarioRepository;

  private final PasswordEncoder passwordEncoder;

  /**
   * Construtor da classe, que recebe o {@link PasswordEncoder} necessário para a criptografia de
   * senhas.
   *
   * @param passwordEncoder Implementação de {@link PasswordEncoder} usada para criptografar senhas
   *     de usuários.
   */
  public UsuarioServiceImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Consulta um {@link Usuario} pelo seu identificador único (UUID).
   *
   * @param id Identificador único do usuário.
   * @return O usuário encontrado ou lança uma exceção {@link ValidacaoNotFoundException} se o
   *     usuário não for encontrado.
   */
  @Override
  public Usuario consultarUsuarioPorId(UUID id) {
    return usuarioRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ValidacaoNotFoundException(
                    EValidacao.USUARIO_NAO_ENCONTRADO_POR_ID, id.toString()));
  }

  /**
   * Cria um novo usuário com base nos dados fornecidos.
   *
   * @param usuario Objeto de solicitação contendo as informações do usuário a ser cadastrado.
   * @return DTO de resposta contendo os dados do usuário criado.
   */
  @Override
  public UsuarioResponseDTO criarUsuario(UsuarioCadastroRequestDTO usuario) {
    validacoesUsuario(usuario);
    return executarComandoComTratamentoErroComMensagemComParseResource(
        () -> usuarioRepository.save(obterUsuario(usuario)),
        ERRO_AO_TENTAR_CRIAR_USUARIO,
        new UsuarioParse());
  }

  /**
   * Altera a senha do usuário logado.
   *
   * @param senha A nova senha a ser configurada para o usuário.
   */
  @Override
  public void alterarSenhaUsuario(String senha) {
    executarComandoComTratamentoSemRetorno(
        () -> {
          Usuario usuario = obterUsuarioLogado();
          assert usuario != null;
          usuario.setSenha(passwordEncoder.encode(senha));
          usuarioRepository.save(usuario);
        });
  }

  /**
   * Atualiza os dados de um usuário com base no ID informado.
   *
   * @param usuario Objeto contendo os novos dados do usuário.
   * @param id Identificador único do usuário a ser atualizado.
   * @return O usuário atualizado.
   */
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

  /**
   * Atualiza os campos não nulos de um objeto de origem em outro de destino.
   *
   * @param origem Objeto de origem contendo os valores novos.
   * @param destino Objeto de destino que será atualizado.
   */
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
        throw new RuntimeException(ERRO_AO_ACESSAR_CAMPO + campo.getName(), e);
      }
    }
  }

  /**
   * Obtém o usuário logado da sessão atual.
   *
   * @return O usuário logado.
   */
  public Usuario carregarUsuarioDaSessao() {
    return obterUsuarioLogado();
  }

  /**
   * Valida se o email já está cadastrado no sistema.
   *
   * @param email Email a ser verificado.
   */
  private void validarSeEmailJaCadastrado(String email) {
    if (usuarioRepository.findByEmail(email).isPresent())
      throw new ValidacaoException(EValidacao.EMAIL_JA_CADASTRADO, email);
  }

  /**
   * Valida se o telefone já está cadastrado no sistema.
   *
   * @param telefone Número de telefone a ser verificado.
   */
  private void validarSeTelefoneJaCadastrado(String telefone) {
    if (usuarioRepository.findByTelefone(telefone).isPresent())
      throw new ValidacaoException(EValidacao.TELEFONE_JA_CADASTRADO, telefone);
  }

  /**
   * Converte os dados de cadastro de um usuário em uma entidade {@link Usuario} com a senha
   * criptografada.
   *
   * @param usuarioCadastroRequestDTO Dados de cadastro do usuário.
   * @return A entidade {@link Usuario} pronta para ser persistida no banco.
   */
  private Usuario obterUsuario(UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    return new UsuarioParse()
        .toEntityComSenha(
            usuarioCadastroRequestDTO,
            passwordEncoder.encode(usuarioCadastroRequestDTO.getSenha()));
  }

  /**
   * Realiza validações nos dados do usuário antes do cadastro.
   *
   * @param usuarioCadastroRequestDTO Dados do usuário a serem validados.
   */
  private void validacoesUsuario(UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    validacaoFormatoEmailETelefone(usuarioCadastroRequestDTO);
    validarSeEmailJaCadastrado(usuarioCadastroRequestDTO.getEmail());
    validarSeTelefoneJaCadastrado(usuarioCadastroRequestDTO.getTelefone());
  }
}
