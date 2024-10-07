package br.com.sonne.cash_flux.shared.enums;

import lombok.Getter;

@Getter
public enum Categoria {
  ALIMENTACAO(1, "Alimentação"),
  TRANSPORTE(2, "Transporte"),
  LAZER(3, "Lazer"),
  SAUDE(4, "Saúde"),
  EDUCACAO(5, "Educação");

  private final int id;
  private final String descricao;

  Categoria(int id, String descricao) {
    this.id = id;
    this.descricao = descricao;
  }

  public static Categoria fromId(int id) {
    for (Categoria categoria : values()) {
      if (categoria.getId() == id) {
        return categoria;
      }
    }
    throw new IllegalArgumentException("ID inválido: " + id);
  }
}
