package br.com.sonne.cash_flux.shared.DTO;

import br.com.sonne.cash_flux.domain.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GastoDTO {

    private String descricao;
    private Double valor;
    private Categoria categoria;  // O enum Categoria é utilizado aqui
    private UUID usuarioId;
    private UUID folhaId;

}
