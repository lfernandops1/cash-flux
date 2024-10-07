package br.com.sonne.cash_flux.shared.DTO;

import br.com.sonne.cash_flux.shared.enums.Role;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public abstract class UsuarioDTO {

  private String email;
  private String senha;
  private Role role;
  private String nome;
  private String sobrenome;
  private String telefone;
  private boolean ativo;
  private LocalDateTime dataHoraAtualizacao;

  private LocalDateTime dataHoraCriacao;
}
