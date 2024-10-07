package br.com.sonne.cash_flux.shared.enums;

import lombok.Getter;

@Getter
public enum Tipo {
  MENSAL(1, "Mensal", "M"),
  FOLHA_AVULSA(2, "Folha Avulsa", "FA"),
  GASTO_AVULSO(3, "Gasto Avulso", "GA");

  private final int id;
  private final String descricao;
  private final String sigla;

  Tipo(int id, String descricao, String sigla) {
    this.id = id;
    this.descricao = descricao;
    this.sigla = sigla;
  }

  public static Tipo fromId(int id) {
    for (Tipo tipo : values()) {
      if (tipo.getId() == id) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("ID inválido: " + id);
  }
}
