package br.com.sonne.cash_flux.config.exception;

import br.com.sonne.cash_flux.shared.enums.EValidacao;

public class ValidacaoException extends ExceptionAbstract {

  public ValidacaoException(EValidacao validacao) {
    super(validacao);
  }

  public ValidacaoException(EValidacao validacao, Throwable cause) {
    super(validacao, cause);
  }

  public ValidacaoException(EValidacao validacao, String... params) {
    super(validacao, params);
  }
}
