package br.com.sonne.cash_flux.shared.enums;

import lombok.Getter;

@Getter
public enum Mes {
  JANEIRO(1, "Janeiro", "JAN"),
  FEVEREIRO(2, "Fevereiro", "FEV"),
  MARCO(3, "Março", "MAR"),
  ABRIL(4, "Abril", "ABR"),
  MAIO(5, "Maio", "MAI"),
  JUNHO(6, "Junho", "JUN"),
  JULHO(7, "Julho", "JUL"),
  AGOSTO(8, "Agosto", "AGO"),
  SETEMBRO(9, "Setembro", "SET"),
  OUTUBRO(10, "Outubro", "OUT"),
  NOVEMBRO(11, "Novembro", "NOV"),
  DEZEMBRO(12, "Dezembro", "DEZ");

  private final int id;
  private final String descricao;
  private final String sigla;

  Mes(int id, String descricao, String sigla) {
    this.id = id;
    this.descricao = descricao;
    this.sigla = sigla;
  }

  public static Mes fromId(int id) {
    for (Mes mes : values()) {
      if (mes.getId() == id) {
        return mes;
      }
    }
    throw new IllegalArgumentException("ID inválido: " + id);
  }
}
