package br.com.sonne.cash_flux.shared.DTO;


import br.com.sonne.cash_flux.domain.Mes;
import lombok.Data;

import java.util.List;

@Data
public class FolhaDTO {

    private Mes mes;
    private List<GastoDTO> gastos;
}
