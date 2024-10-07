package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import jakarta.validation.constraints.NotBlank;

public class GastoCadastroRequest extends GastoDTO {
  @NotBlank private String descricao;
  @NotBlank private String valor;
  @NotBlank String tipo;
  @NotBlank String categoria;
}
