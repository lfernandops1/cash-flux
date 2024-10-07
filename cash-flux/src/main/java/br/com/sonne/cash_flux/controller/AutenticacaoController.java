package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.service.impl.AutenticacaoServiceImpl;
import br.com.sonne.cash_flux.shared.DTO.AutenticacaoDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticar")
public class AutenticacaoController {

  private final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoController.class);

  @Autowired private final AutenticacaoServiceImpl autenticacaoServiceImpl;

  public AutenticacaoController(AutenticacaoServiceImpl autenticacaoServiceImpl) {
    this.autenticacaoServiceImpl = autenticacaoServiceImpl;
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
    return autenticacaoServiceImpl.login(autenticacaoDTO);
  }
}
