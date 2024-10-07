package br.com.sonne.cash_flux.shared.DTO.response;

import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GastoResponseDTO extends GastoDTO {

    private UUID id;
    private String descricao;
    private Double valor;
    private String categoria;
    private String tipo;
}
