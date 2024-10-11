package br.com.sonne.cash_flux.shared.parse;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.shared.DTO.request.GastoAlterarRequest;
import br.com.sonne.cash_flux.shared.DTO.request.GastoRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.GastoResponseDTO;
import br.com.sonne.cash_flux.shared.sample.Parse;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class GastoParse implements Parse<GastoRequestDTO, Gasto, GastoResponseDTO> {

  @Override
  public Gasto toEntity(GastoRequestDTO requestDTO) {
    Gasto gasto = new Gasto();
    gasto.setDescricao(requestDTO.getDescricao());
    gasto.setValor(requestDTO.getValor());
    gasto.setTipo(requestDTO.getTipo());
    gasto.setUsuario(requestDTO.getUsuario());
    gasto.setFolha(requestDTO.getFolha());
    gasto.setCategoria(requestDTO.getCategoria());
    gasto.setDataHoraCriacao(requestDTO.getDataHoraCriacao());
    gasto.setDataHoraAtualizacao(requestDTO.getDataHoraAtualizacao());
    return gasto;
  }

  @Override
  public GastoResponseDTO toResponse(Gasto gasto) {
    GastoResponseDTO response = new GastoResponseDTO();
    response.setId(gasto.getId());
    response.setDescricao(gasto.getDescricao());
    response.setValor(gasto.getValor());
    response.setCategoria(gasto.getCategoria());
    response.setTipo(gasto.getTipo());
    return response;
  }

  private Gasto retornarGastos(GastoResponseDTO responseDTO) {
    Gasto gasto = new Gasto();
    gasto.setId(responseDTO.getId());
    return gasto;
  }

  public Gasto alterarRequestToEntity(GastoAlterarRequest request) {
    Gasto gasto = new Gasto();
    gasto.setDescricao(request.getDescricao());
    gasto.setValor(request.getValor());
    gasto.setCategoria(request.getCategoria());
    gasto.setTipo(request.getTipo());
    gasto.setDataHoraAtualizacao(LocalDateTime.now());
    return gasto;
  }
}
