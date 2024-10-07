package br.com.sonne.cash_flux.config.exception.handler;

import br.com.sonne.cash_flux.config.exception.ExceptionAbstract;
import br.com.sonne.cash_flux.config.exception.ExceptionNotFoundAbstract;
import br.com.sonne.cash_flux.shared.DTO.erro.ErroDTO;
import br.com.sonne.cash_flux.shared.DTO.erro.ErrosDTO;
import br.com.sonne.cash_flux.shared.enums.EValidacao;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidacaoHandler {
  @ExceptionHandler(ExceptionNotFoundAbstract.class)
  public ResponseEntity<Object> validacaoHandleNotFound(ExceptionNotFoundAbstract ex) {
    List<ErroDTO> erros = new ArrayList<>();
    erros.add(ErroDTO.builder().codigo(ex.getCodigo()).mensagem(ex.getMensagem()).build());
    log.warn(ex.getMessage());
    return new ResponseEntity<>(ErrosDTO.builder().erros(erros).build(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ExceptionAbstract.class)
  public ResponseEntity<Object> validacaoHandle(ExceptionAbstract ex) {
    if (ex.getCodigo().equals(EValidacao.NAO_IDENTIFICADO.getCodigo())) {
      log.error("Ocorreu um erro interno", ex);
      return new ResponseEntity<>(
          ErroDTO.builder().codigo(ex.getCodigo()).mensagem(ex.getMensagem()).build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      List<ErroDTO> erros = new ArrayList<>();
      erros.add(ErroDTO.builder().codigo(ex.getCodigo()).mensagem(ex.getMensagem()).build());
      log.warn(ex.getMensagem());
      return new ResponseEntity<>(ErrosDTO.builder().erros(erros).build(), HttpStatus.BAD_REQUEST);
    }
  }
}
