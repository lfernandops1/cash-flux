package br.com.sonne.cash_flux.shared.DTO;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Usuario;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GastoDTO {

  private String descricao;
  private Double valor;
  private String categoria;
  private Usuario usuario;
  private Folha folha;
  private String tipo;
  private LocalDateTime dataHoraCriacao;
  private LocalDateTime dataHoraAtualizacao;
}
