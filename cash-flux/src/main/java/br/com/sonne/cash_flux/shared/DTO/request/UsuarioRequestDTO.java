package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.shared.DTO.UsuarioDTO;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UsuarioRequestDTO extends UsuarioDTO {

  @NotNull private UUID id;
}
