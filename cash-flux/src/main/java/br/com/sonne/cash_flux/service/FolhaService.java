package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import br.com.sonne.cash_flux.domain.Folha;

import java.util.List;
import java.util.UUID;

public interface FolhaService {

    Folha criarFolha(FolhaDTO folhaDTO);

    Folha atualizarFolha(UUID folhaId, List<GastoDTO> gastoDTOs);

    List<Folha> listarTodasFolhas();
}
