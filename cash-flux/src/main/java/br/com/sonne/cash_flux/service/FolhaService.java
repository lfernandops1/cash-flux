package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import java.util.List;
import java.util.UUID;

public interface FolhaService {

  Folha criarFolha(FolhaRequestDTO folhaDTO);

  // Folha atualizarFolha(UUID folhaId, List<GastoDTO> gastoDTOs);

  List<Folha> listarTodasFolhasUsuario(UUID uuid);

  List<Folha> listarPorTipo(String tipo);
}
