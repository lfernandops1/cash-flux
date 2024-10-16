package br.com.sonne.cash_flux.service;

import br.com.sonne.cash_flux.domain.CategoriaUsuario;
import br.com.sonne.cash_flux.domain.Usuario;

public interface CategoriaUsuarioService {
  CategoriaUsuario criarNovaCategoria(String descricao, Usuario usuario);
}
