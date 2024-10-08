package br.com.sonne.cash_flux.shared.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacaoDTO {

  private String email;
  private String senha;
  private Boolean ativo;
}
