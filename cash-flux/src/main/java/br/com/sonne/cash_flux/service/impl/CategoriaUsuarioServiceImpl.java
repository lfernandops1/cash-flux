package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.util.MetodosUteis.gerarCodigoAleatorio;

import br.com.sonne.cash_flux.domain.CategoriaUsuario;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.CategoriaUsuarioRepository;
import br.com.sonne.cash_flux.service.CategoriaUsuarioService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaUsuarioServiceImpl implements CategoriaUsuarioService {

  @Autowired private CategoriaUsuarioRepository categoriaUsuarioRepository;

  public CategoriaUsuario criarNovaCategoria(String descricao, Usuario usuario) {
    CategoriaUsuario novaCategoria = new CategoriaUsuario();
    novaCategoria.setId(UUID.randomUUID());
    novaCategoria.setDescricao(descricao);
    novaCategoria.setCodigo(gerarCodigoAleatorio());
    novaCategoria.setUsuario(usuario);
    novaCategoria.setDataHoraCriacao(LocalDateTime.now());
    novaCategoria.setDataHoraAtualizacao(LocalDateTime.now());
    novaCategoria.setDataHoraExclusao(null);
    return categoriaUsuarioRepository.save(novaCategoria);
  }
}
