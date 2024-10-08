package br.com.sonne.cash_flux.shared.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SenhaRequestDTO {
  @NotBlank private String senha;
}
