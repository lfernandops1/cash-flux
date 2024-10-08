package br.com.sonne.cash_flux.config.exception;

import br.com.sonne.cash_flux.shared.enums.EValidacao;
import lombok.Getter;

@Getter
public class CashFluxRuntimeException extends RuntimeException {
  private final String descricao;

  public CashFluxRuntimeException(String msg) {
    super(msg);
    this.descricao = msg;
  }

  public CashFluxRuntimeException(EValidacao validacao) {
    super(validacao.getDescricao());
    this.descricao = validacao.getDescricao();
  }

  public CashFluxRuntimeException(String msg, Throwable causa) {
    super(msg, causa);
    this.descricao = msg;
  }
}
