package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.shared.DTO.UsuarioDTO;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsuarioAlterarRequestDTO extends UsuarioDTO {

  private UUID id;
  private String nome;
  private String sobrenome;
  private String email;
  private String telefone;
}
