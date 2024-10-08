package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.shared.DTO.UsuarioDTO;
import br.com.sonne.cash_flux.shared.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCadastroRequestDTO extends UsuarioDTO {

  private String nome;
  private String sobrenome;
  private String email;
  private String telefone;
  private String senha;
  private Role role;
}
