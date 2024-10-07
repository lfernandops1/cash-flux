package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import java.util.List;
import java.util.UUID;

public interface GastoService {

  // Gasto criarGasto(Gasto gasto);

  Gasto criarGastoAvulso(Gasto gasto);

  void criarGastosEmFolha(FolhaDTO folhaDTO, Folha folha);

  Gasto buscarGastoPorId(UUID id);

  List<Gasto> listarGastos();

  Gasto atualizarGasto(UUID id, Gasto gastoAtualizado);

  void deletarGasto(UUID id);
}
