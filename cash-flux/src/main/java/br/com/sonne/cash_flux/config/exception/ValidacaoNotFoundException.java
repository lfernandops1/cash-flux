package br.com.sonne.cash_flux.config.exception;

import br.com.sonne.cash_flux.shared.enums.EValidacao;

public class ValidacaoNotFoundException extends ExceptionNotFoundAbstract {

  public ValidacaoNotFoundException(EValidacao validacao, String... params) {
    super(validacao, params);
  }
}
