package br.com.sonne.cash_flux.shared.DTO;

import br.com.sonne.cash_flux.shared.enums.Categoria;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import jakarta.validation.constraints.NotNull;

public record CadastroGastoDTO(
    @NotNull String descricao,
    @NotNull Tipo tipo,
    @NotNull Categoria categoria,
    @NotNull Double valor) {}
