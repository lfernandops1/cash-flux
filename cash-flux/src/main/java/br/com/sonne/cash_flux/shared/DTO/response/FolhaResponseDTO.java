package br.com.sonne.cash_flux.shared.DTO.response;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolhaResponseDTO extends FolhaDTO {

  private UUID id;
  private String mes;
  private String descricao;
  private List<Gasto> gastos;
  private String tipo;
  private LocalDateTime dataHoraAtualizacao;
  private LocalDateTime dataHoraCriacao;
}
