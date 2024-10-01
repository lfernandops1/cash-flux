package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.shared.DTO.TokenDTO;
import br.com.sonne.cash_flux.shared.DTO.request.AutenticacaoRequest;
import br.com.sonne.cash_flux.domain.Usuario;

public interface AutenticacaoService {

    TokenDTO autenticar(AutenticacaoRequest autenticacaoRequest);


    public void criarAutenticacao(Usuario usuario);
}
