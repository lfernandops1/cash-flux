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

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired private UsuarioService usuarioService;

  @Autowired private UsuarioParse usuarioParse;

  public UsuarioController(UsuarioParse usuarioParse) {
    this.usuarioParse = usuarioParse;
  }

  @PostMapping("/criar")
  public ResponseEntity<UsuarioResponseDTO> criarUsuario(
      @RequestBody @Valid UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    return new ResponseEntity<>(
        usuarioService.criarUsuario(usuarioCadastroRequestDTO), HttpStatus.CREATED);
  }

  @PatchMapping(value = "/senha")
  public ResponseEntity<Void> alterarSenhaUsuario(
      @Valid @RequestBody SenhaRequestDTO senhaRequestDTO) {
    usuarioService.alterarSenhaUsuario(senhaRequestDTO.getSenha());
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
      @Valid @RequestBody UsuarioAlterarRequestDTO usuarioAlterarRequestDTO,
      @PathVariable UUID id) {
    Usuario usuarioAlterar =
        usuarioService.atualizar(usuarioParse.alterarRequestToEntity(usuarioAlterarRequestDTO), id);
    return new ResponseEntity<>(usuarioParse.toResponse(usuarioAlterar), HttpStatus.OK);
  }
}
