package br.com.sonne.cash_flux.shared.DTO;

import br.com.sonne.cash_flux.domain.Gasto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FolhaDTO {

    private String mes;
    private String descricao;
    private List<Gasto> gastos;
    private String tipo;
    private LocalDateTime dataHoraAtualizacao;
    private LocalDateTime dataHoraCriacao;
}
