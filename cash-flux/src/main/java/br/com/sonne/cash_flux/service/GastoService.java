package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import java.util.List;
import java.util.UUID;

public interface GastoService {

  Gasto criarGastoAvulso(Gasto gasto);

  void criarGastosEmFolha(FolhaDTO folhaDTO, Folha folha);

  Gasto buscarGastoPorId(UUID id);

  List<Gasto> listarTodosGastosAvulsos();

  Gasto atualizarGasto(Gasto gastoAtualizado, UUID id);

  void atualizarGastosEmFolha(FolhaRequestDTO folhaDTO, Folha folha);

  void excluirGastos(UUID id);
}
