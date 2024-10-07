package br.com.sonne.cash_flux.shared.parse;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.FolhaResponseDTO;
import br.com.sonne.cash_flux.shared.DTO.response.GastoResponseDTO;
import br.com.sonne.cash_flux.shared.sample.Parse;
import org.springframework.stereotype.Component;

@Component
public class FolhaParse implements Parse<FolhaRequestDTO, Folha, FolhaResponseDTO> {

  @Override
  public Folha toEntity(FolhaRequestDTO requestDTO) {
    Folha folha = new Folha();
    folha.setDescricao(requestDTO.getDescricao());
    folha.setGastos(requestDTO.getGastos());
    folha.setTipo(requestDTO.getTipo());
    folha.setUsuario(requestDTO.getUsuario());
    folha.setDataHoraCriacao(requestDTO.getDataHoraCriacao());
    folha.setDataHoraAtualizacao(requestDTO.getDataHoraAtualizacao());
    return folha;
  }

  public FolhaRequestDTO toRequestDTO(Folha folha) {
    FolhaRequestDTO requestDTO = new FolhaRequestDTO();

    requestDTO.setDescricao(folha.getDescricao());
    requestDTO.setGastos(folha.getGastos());
    requestDTO.setTipo(folha.getTipo());
    requestDTO.setUsuario(folha.getUsuario());
    requestDTO.setDataHoraCriacao(folha.getDataHoraCriacao());
    requestDTO.setDataHoraAtualizacao(folha.getDataHoraAtualizacao());
    return requestDTO;
  }

  @Override
  public FolhaResponseDTO toResponse(Folha folha) {
    FolhaResponseDTO response = new FolhaResponseDTO();
    response.setId(folha.getId());
    response.setDescricao(folha.getDescricao());
    response.setGastos(folha.getGastos());
    response.setMes(folha.getMes());
    response.setTipo(folha.getTipo());
    return response;
  }

  private Folha retornarFolha(GastoResponseDTO responseDTO) {
    Folha folha = new Folha();
    folha.setId(responseDTO.getId());
    return folha;
  }
}
