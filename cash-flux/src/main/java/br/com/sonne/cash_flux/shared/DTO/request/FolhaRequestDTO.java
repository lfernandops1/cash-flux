package br.com.sonne.cash_flux.shared.DTO.request;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolhaRequestDTO extends FolhaDTO {

  private String mes;
  private String descricao;
  private List<Gasto> gastos;
  private String tipo;
  private Usuario usuario;
}
