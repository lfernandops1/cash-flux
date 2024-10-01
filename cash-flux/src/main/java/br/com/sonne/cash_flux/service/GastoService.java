package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Gasto;

import java.util.List;
import java.util.UUID;

public interface GastoService {

    Gasto criarGasto(Gasto gasto);

    Gasto buscarGastoPorId(UUID id);

    List<Gasto> listarGastos();

    Gasto atualizarGasto(UUID id, Gasto gastoAtualizado);

    void deletarGasto(UUID id);

}
