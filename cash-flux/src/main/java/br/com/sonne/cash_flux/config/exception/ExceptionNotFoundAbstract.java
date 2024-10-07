package br.com.sonne.cash_flux.config.exception;

import br.com.sonne.cash_flux.shared.enums.EValidacao;
import lombok.Getter;

public abstract class ExceptionNotFoundAbstract extends RuntimeException {

  private EValidacao validacao;
  @Getter private String[] params;

  public Integer getCodigo() {
    return this.validacao.getCodigo();
  }

  public String getMensagem() {
    return this.validacao.getDescricao(this.params);
  }

  public ExceptionNotFoundAbstract(EValidacao validacao) {
    super(validacao.getDescricao());
    this.validacao = validacao;
  }

  public ExceptionNotFoundAbstract(EValidacao validacao, String... params) {
    super(validacao.getDescricao());
    this.validacao = validacao;
    this.params = params;
  }

  public ExceptionNotFoundAbstract(EValidacao validacao, Throwable cause) {
    super(validacao.getDescricao(), cause);
    this.validacao = validacao;
  }
}
