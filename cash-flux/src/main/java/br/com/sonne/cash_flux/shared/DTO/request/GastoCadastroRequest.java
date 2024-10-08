package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GastoCadastroRequest extends GastoDTO {
  private String descricao;
  private String valor;
  private String tipo;
  private String categoria;
}
