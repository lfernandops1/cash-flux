package br.com.sonne.cash_flux.shared.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
}
