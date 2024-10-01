package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.shared.DTO.TokenDTO;
import br.com.sonne.cash_flux.shared.DTO.request.AutenticacaoRequest;
import br.com.sonne.cash_flux.service.AutenticacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/autenticar")
public class AutenticacaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoController.class);

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> autenticar(@Validated @RequestBody AutenticacaoRequest autenticacaoRequest) {
        LOGGER.debug("Requisição REST para autenticar com a api e obter token jwt: {}", autenticacaoRequest);
        TokenDTO tokenDTO = this.autenticacaoService.autenticar(autenticacaoRequest);
        return ResponseEntity.ok(tokenDTO);
    }
}