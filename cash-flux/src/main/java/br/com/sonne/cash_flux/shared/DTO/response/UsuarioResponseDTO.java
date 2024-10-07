package br.com.sonne.cash_flux.shared.DTO.response;

import br.com.sonne.cash_flux.shared.DTO.UsuarioDTO;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO extends UsuarioDTO {

  private UUID id;
  private String nome;
  private String sobrenome;
  private String email;
  private String telefone;
}
