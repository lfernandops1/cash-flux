package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.shared.DTO.FolhaFiltroDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolhaService {

  Folha criarFolha(FolhaRequestDTO folhaDTO);

  List<Folha> listarTodasFolhasUsuario();

  List<Folha> listarPorFiltros(FolhaFiltroDTO dto);

  Optional<Folha> buscarPorId(UUID id);

  void excluirFolha(UUID id);

  Folha alterarFolha(UUID id, FolhaRequestDTO folhaDTO);
}
