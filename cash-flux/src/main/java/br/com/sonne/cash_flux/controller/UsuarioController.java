package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.UsuarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired private UsuarioService usuarioService;

  @PostMapping("/criar")
  public ResponseEntity<UsuarioResponseDTO> criarUsuario(
      @RequestBody @Valid UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    return new ResponseEntity<>(
        usuarioService.criarUsuario(usuarioCadastroRequestDTO), HttpStatus.CREATED);
  }
}
