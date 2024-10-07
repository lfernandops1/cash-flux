package br.com.sonne.cash_flux.config.exception.handler;

import br.com.sonne.cash_flux.config.exception.CashFluxRuntimeException;
import br.com.sonne.cash_flux.shared.DTO.erro.ErroDTO;
import br.com.sonne.cash_flux.shared.DTO.erro.ErrosDTO;
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
public class RuntimeExceptionHandler {

  @ExceptionHandler(CashFluxRuntimeException.class)
  public ResponseEntity<Object> validacaoHandle(CashFluxRuntimeException ex) {
    List<ErroDTO> erros = new ArrayList<>();

    erros.add(ErroDTO.builder().codigo(-999).mensagem(ex.getDescricao()).build());
    log.error("Ocorreu um erro interno", ex);

    return new ResponseEntity<>(
        ErrosDTO.builder().erros(erros).build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
