package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GastoAlterarRequest extends GastoDTO {

  private UUID id;
  private String descricao;
  private Double valor;
  private String categoria;
  private Folha folha;
  private String tipo;
}
