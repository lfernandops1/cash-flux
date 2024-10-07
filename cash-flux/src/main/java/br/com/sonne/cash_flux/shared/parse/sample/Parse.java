package br.com.sonne.cash_flux.shared.parse.sample;

import static java.util.Objects.isNull;

import br.com.sonne.cash_flux.config.exception.CashFluxRuntimeException;
import java.util.List;
import java.util.stream.Collectors;

public interface Parse<RequestDTO, Entity, ResponseDTO> {

  default ResponseDTO toResponse(Entity entity) {
    throw new CashFluxRuntimeException("Necessita programação!");
  }

  default List<Entity> toEntityList(List<RequestDTO> requestDTOS) {
    if (isNull(requestDTOS) || requestDTOS.isEmpty()) return null;
    return requestDTOS.stream().map(this::toEntity).collect(Collectors.toList());
  }

  default List<ResponseDTO> toResponseList(List<Entity> entities) {
    if (isNull(entities) || entities.isEmpty()) return null;
    return entities.stream().map(this::toResponse).collect(Collectors.toList());
  }

  default Entity toEntity(RequestDTO requestDTO) {
    throw new CashFluxRuntimeException("Necessita programação!");
  }
}
