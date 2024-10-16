package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.Constantes.Mensagens.ERRO_AO_EXCLUIR_FOLHA;
import static br.com.sonne.cash_flux.shared.Constantes.Mensagens.FOLHA_NAO_ENCONTRADA;
import static br.com.sonne.cash_flux.shared.Constantes.Util.SEM_DESCRICAO;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;
import static br.com.sonne.cash_flux.shared.util.SecurityUtil.obterUsuarioLogado;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.FolhaRepository;
import br.com.sonne.cash_flux.service.FolhaService;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.FolhaFiltroDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import br.com.sonne.cash_flux.specification.FolhaFiltroSpecification;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FolhaServiceImpl implements FolhaService {

  @Autowired private FolhaRepository folhaRepository;

  @Autowired private GastoService gastoService;

  @Autowired private UsuarioService usuarioService;

  public Folha criarFolha(FolhaRequestDTO folhaDTO) {
    Folha folha = obterFolha(folhaDTO);
    folha = folhaRepository.save(folha);
    gastoService.criarGastosEmFolha(folhaDTO, folha);
    folha =
        folhaRepository
            .findById(folha.getId())
            .orElseThrow(() -> new EntityNotFoundException(FOLHA_NAO_ENCONTRADA));

    return folha;
  }

  public List<Folha> listarTodasFolhasUsuario() {
    UUID usuarioId = usuarioService.carregarUsuarioDaSessao().getId();
    List<Folha> folhas = folhaRepository.findByUsuarioIdAndDataHoraExclusaoIsNull(usuarioId);

    if (folhas != null && !folhas.isEmpty()) {
      for (Folha folha : folhas) {
        if (folha.getDescricao() == null || folha.getDescricao().isEmpty()) {
          folha.setDescricao(SEM_DESCRICAO);
        }
      }
    }

    return folhas;
  }

  public List<Folha> listarPorFiltros(FolhaFiltroDTO filtro) {
    Specification<Folha> specification = FolhaFiltroSpecification.comFiltros(filtro);
    return folhaRepository.findAll(specification);
  }

  private static Folha obterFolha(FolhaDTO folhaDTO) {
    Folha folha = new Folha();
    Usuario usuario = obterUsuarioLogado();
    folha.setTipo(folhaDTO.getTipo());
    folha.setMes(
        folha.getTipo().equals(Tipo.FOLHA_AVULSA.getDescricao()) ? null : folhaDTO.getMes());
    folha.setDescricao(folhaDTO.getDescricao());
    folha.setUsuario(usuario);
    folha.setDataHoraAtualizacao(LocalDateTime.now());
    folha.setDataHoraCriacao(LocalDateTime.now());
    folha.setGastos(new ArrayList<>());
    return folha;
  }

  @Override
  public Optional<Folha> buscarPorId(UUID id) {
    return folhaRepository.findById(id);
  }

  @Override
  public void excluirFolha(UUID id) {
    executarComandoComTratamentoSemRetornoComMensagem(
        () -> {
          Folha folhaExcluida =
              folhaRepository
                  .findById(id)
                  .orElseThrow(() -> new EntityNotFoundException(FOLHA_NAO_ENCONTRADA));
          folhaExcluida.setDataHoraExclusao(LocalDateTime.now());
          folhaRepository.save(folhaExcluida);
          gastoService.excluirGastos(folhaExcluida.getId());
        },
        ERRO_AO_EXCLUIR_FOLHA);
  }

  public Folha alterarFolha(UUID id, FolhaRequestDTO folhaDTO) {
    Folha folhaExistente =
        folhaRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(FOLHA_NAO_ENCONTRADA));

    boolean folhaAlterada = false;

    if (!folhaExistente.getTipo().equals(folhaDTO.getTipo())) {
      folhaExistente.setTipo(folhaDTO.getTipo());
      folhaAlterada = true;
    }

    if (!folhaExistente
        .getMes()
        .equals(
            folhaExistente.getTipo().equals(Tipo.FOLHA_AVULSA.getDescricao())
                ? null
                : folhaDTO.getMes())) {
      folhaExistente.setMes(
          folhaExistente.getTipo().equals(Tipo.FOLHA_AVULSA.getDescricao())
              ? null
              : folhaDTO.getMes());
      folhaAlterada = true;
    }

    if (!folhaExistente.getDescricao().equals(folhaDTO.getDescricao())) {
      folhaExistente.setDescricao(folhaDTO.getDescricao());
      folhaAlterada = true;
    }

    if (folhaAlterada) {
      folhaExistente.setDataHoraAtualizacao(LocalDateTime.now());
      folhaRepository.save(folhaExistente);
    }

    gastoService.atualizarGastosEmFolha(folhaDTO, folhaExistente);

    return folhaExistente;
  }
}
