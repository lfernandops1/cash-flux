package br.com.sonne.cash_flux.shared.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolhaFiltroDTO {
  private String tipo;
  private Double valorMin;
  private Double valorMax;
  private String descricao;
  private String ordenarPor;
  private boolean ascendente;
  private String mes;
}
