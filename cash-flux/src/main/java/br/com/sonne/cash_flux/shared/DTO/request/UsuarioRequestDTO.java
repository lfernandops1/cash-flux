package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.shared.DTO.UsuarioDTO;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO extends UsuarioDTO {

  private UUID id;
}
