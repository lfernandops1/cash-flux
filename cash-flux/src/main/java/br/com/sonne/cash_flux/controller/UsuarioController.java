package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.request.SenhaRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioAlterarRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import br.com.sonne.cash_flux.shared.parse.UsuarioParse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gerenciar operações relacionadas aos usuários. Este controlador expõe
 * endpoints para criar, atualizar e alterar a senha de um usuário. @RestController indica que esta
 * classe é um controlador onde cada método retornará um objeto que será serializado em JSON e
 * enviado como resposta. @RequestMapping define o caminho base "/api/usuarios" para todos os
 * endpoints neste controlador.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired private UsuarioService usuarioService;

  @Autowired private UsuarioParse usuarioParse;

  /**
   * Construtor para a classe UsuarioController. Injeta a dependência de UsuarioParse.
   *
   * @param usuarioParse O parse responsável por converter DTOs em entidades de domínio e
   *     vice-versa.
   */
  public UsuarioController(UsuarioParse usuarioParse) {
    this.usuarioParse = usuarioParse;
  }

  /**
   * Endpoint para criar um novo usuário.
   *
   * <p>Este método cria um novo usuário a partir das informações fornecidas no corpo da requisição.
   *
   * @param usuarioCadastroRequestDTO Objeto que contém os dados de cadastro do usuário.
   * @return Um ResponseEntity contendo o DTO de resposta do usuário criado, junto com o status HTTP
   *     201 (CREATED). @PostMapping associa este método ao endpoint HTTP POST "/criar".
   */
  @PostMapping("/criar")
  public ResponseEntity<UsuarioResponseDTO> criarUsuario(
      @RequestBody @Valid UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    return new ResponseEntity<>(
        usuarioService.criarUsuario(usuarioCadastroRequestDTO), HttpStatus.CREATED);
  }

  /**
   * Endpoint para alterar a senha do usuário.
   *
   * <p>Este método permite que o usuário altere sua senha a partir de uma requisição HTTP PATCH.
   *
   * @param senhaRequestDTO Objeto que contém a nova senha.
   * @return Um ResponseEntity sem corpo, indicando sucesso, com o status HTTP 204 (No
   *     Content). @PatchMapping associa este método ao endpoint HTTP PATCH "/senha".
   */
  @PatchMapping(value = "/senha")
  public ResponseEntity<Void> alterarSenhaUsuario(
      @Valid @RequestBody SenhaRequestDTO senhaRequestDTO) {
    usuarioService.alterarSenhaUsuario(senhaRequestDTO.getSenha());
    return ResponseEntity.noContent().build();
  }

  /**
   * Endpoint para atualizar as informações de um usuário.
   *
   * <p>Este método permite a atualização dos dados de um usuário já existente, identificando-o pelo
   * ID.
   *
   * @param usuarioAlterarRequestDTO Objeto que contém os novos dados do usuário.
   * @param id O identificador único do usuário que será atualizado.
   * @return Um ResponseEntity contendo o DTO de resposta do usuário atualizado, com o status HTTP
   *     200 (OK). @PutMapping associa este método ao endpoint HTTP PUT "/atualizar/{id}".
   */
  @PutMapping("/atualizar/{id}")
  public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
      @Valid @RequestBody UsuarioAlterarRequestDTO usuarioAlterarRequestDTO,
      @PathVariable UUID id) {
    Usuario usuarioAlterar =
        usuarioService.atualizar(usuarioParse.alterarRequestToEntity(usuarioAlterarRequestDTO), id);
    return new ResponseEntity<>(usuarioParse.toResponse(usuarioAlterar), HttpStatus.OK);
  }
}
