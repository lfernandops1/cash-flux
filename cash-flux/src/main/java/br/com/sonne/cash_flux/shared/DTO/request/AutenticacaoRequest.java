package br.com.sonne.cash_flux.shared.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacaoRequest {

    private String email;

    private String senha;

}
