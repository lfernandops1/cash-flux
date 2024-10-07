package br.com.sonne.cash_flux.shared.enums;

import br.com.sonne.cash_flux.shared.interfaces.IEnumLabel;
import lombok.Getter;

@Getter
public enum EValidacao implements IEnumLabel<EValidacao> {
  ENTRADA_DE_DADOS_INVALIDA(-1),
  CAMPO_INVALIDO_NAO_IDENTIFICADO(-2),
  EMAIL_JA_CADASTRADO(-3),
  TELEFONE_JA_CADASTRADO(-3),
  EMAIL_NAO_ENCONTRADO(-4),
  USUARIO_NAO_ENCONTRADO_POR_ID(-5),
  USUARIO_NAO_ENCONTRADO_POR_EMAIL(-8),
  NAO_IDENTIFICADO(-999);
  private final Integer codigo;

  EValidacao(Integer codigo) {
    this.codigo = codigo;
  }

  public static EValidacao valueOf(Integer codigo) {
    for (EValidacao val : EValidacao.values()) {
      if (val.codigo.equals(codigo)) return val;
    }
    throw new RuntimeException("Erro não cadastrado");
  }
}
