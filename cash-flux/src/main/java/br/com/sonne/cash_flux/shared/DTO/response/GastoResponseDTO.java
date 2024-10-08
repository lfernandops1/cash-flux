package br.com.sonne.cash_flux.shared.DTO.response;

import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GastoResponseDTO extends GastoDTO {

  private UUID id;
  private String descricao;
  private Double valor;
  private String categoria;
  private String tipo;
}
