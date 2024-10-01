package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.shared.DTO.request.UsuarioRequest;
import br.com.sonne.cash_flux.domain.Usuario;
import jakarta.mail.MessagingException;

public interface UsuarioService {

    Usuario criarUsuario(UsuarioRequest usuarioRequest) throws MessagingException;
}
