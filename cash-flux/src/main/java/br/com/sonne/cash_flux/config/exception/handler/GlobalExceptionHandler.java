package br.com.sonne.cash_flux.config.exception.handler;

import br.com.sonne.cash_flux.config.exception.EmailEmUsoException;
import br.com.sonne.cash_flux.config.exception.TelefoneEmUsoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmailEmUsoException.class)
  public ResponseEntity<String> handleEmailEmUsoException(EmailEmUsoException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(TelefoneEmUsoException.class)
  public ResponseEntity<String> handleTelefoneEmUsoException(TelefoneEmUsoException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
