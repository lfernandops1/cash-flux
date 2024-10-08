package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;

import br.com.sonne.cash_flux.config.exception.CashFluxRuntimeException;
import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.repository.GastoRepository;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoServiceImpl implements GastoService {

  private final GastoRepository gastoRepository;

  @Autowired private UsuarioService usuarioService;

  @Autowired
  public GastoServiceImpl(GastoRepository gastoRepository) {
    this.gastoRepository = gastoRepository;
  }

  @Override
  public Gasto buscarGastoPorId(UUID id) {
    Optional<Gasto> gasto = gastoRepository.findById(id);
    if (gasto.isPresent()) {
      return gasto.get();
    } else {
      throw new RuntimeException("Gasto não encontrado com o ID: " + id);
    }
  }

  @Override
  public List<Gasto> listarGastos() {
    return gastoRepository.findAll();
  }

  @Override
  public Gasto criarGastoAvulso(Gasto gastoRequestDTO) {
    return executarComandoComTratamentoErroComMensagem(
        () -> {
          Gasto gasto = new Gasto();
          gasto.setUsuario(usuarioService.carregarUsuarioDaSessao());
          gasto.setDataHoraCriacao(LocalDateTime.now());
          gasto.setDataHoraAtualizacao(LocalDateTime.now());
          gasto.setTipo(Tipo.GASTO_AVULSO.getDescricao().toUpperCase());
          gasto.setValor(gastoRequestDTO.getValor());
          gasto.setCategoria(gastoRequestDTO.getCategoria().toUpperCase());
          return gastoRepository.save(gasto);
        },
        "Erro ao cadastrar gasto avulso");
  }

  @Override
  public void criarGastosEmFolha(FolhaDTO folhaDTO, Folha folha) {
    if (folhaDTO.getGastos() != null) {
      for (Gasto gastoDTO : folhaDTO.getGastos()) {
        Gasto gasto = new Gasto();
        gasto.setDescricao(gastoDTO.getDescricao());
        gasto.setCategoria(gastoDTO.getCategoria());
        gasto.setUsuario(folha.getUsuario());
        gasto.setValor(gastoDTO.getValor());
        gasto.setFolha(folha);
        gasto.setDataHoraAtualizacao(LocalDateTime.now());
        gasto.setDataHoraCriacao(LocalDateTime.now());
        gasto.setTipo(Tipo.MENSAL.getDescricao());
        gastoRepository.save(gasto);
      }
    }
  }

  @Override
  public Gasto atualizarGasto(UUID id, Gasto gastoAtualizado) {
    if (!gastoRepository.existsById(id)) {
      throw new CashFluxRuntimeException("Gasto não encontrado com o ID: " + id);
    }
    gastoAtualizado.setId(id);
    return gastoRepository.save(gastoAtualizado);
  }

  public void atualizarGastosEmFolha(FolhaRequestDTO folhaDTO, Folha folha) {
    // Obtemos os gastos existentes da folha
    List<Gasto> gastosExistentes = folha.getGastos();

    // Cria um mapa para facilitar a busca dos gastos existentes
    Map<UUID, Gasto> mapaGastosExistentes =
        gastosExistentes.stream().collect(Collectors.toMap(Gasto::getId, gasto -> gasto));

    // Atualiza ou mantém os gastos existentes
    for (Gasto gastoDTO : folhaDTO.getGastos()) {
      Optional<Gasto> gastoExistenteOpt =
          Optional.ofNullable(mapaGastosExistentes.get(gastoDTO.getId()));

      if (gastoExistenteOpt.isPresent()) {
        // Se o gasto já existe, verifica se houve alteração
        Gasto gastoExistente = gastoExistenteOpt.get();
        if (atualizarGastoSeNecessario(gastoExistente, gastoDTO)) {
          gastoRepository.save(gastoExistente);
        }
      } else {
        // Se o gasto não existe, cria um novo
        criarGasto(folha, gastoDTO);
      }
    }

    // Remove gastos que não estão mais na DTO
    removerGastosNaoCorrespondentes(gastosExistentes, folhaDTO.getGastos());
  }

  private boolean atualizarGastoSeNecessario(Gasto gastoExistente, Gasto gastoDTO) {
    boolean alterado = false;

    if (!gastoExistente.getDescricao().equals(gastoDTO.getDescricao())) {
      gastoExistente.setDescricao(gastoDTO.getDescricao());
      alterado = true;
    }

    if (!gastoExistente.getCategoria().equals(gastoDTO.getCategoria())) {
      gastoExistente.setCategoria(gastoDTO.getCategoria());
      alterado = true;
    }

    if (!Objects.equals(gastoExistente.getValor(), gastoDTO.getValor())) {
      gastoExistente.setValor(gastoDTO.getValor());
      alterado = true;
    }

    if (alterado) {
      gastoExistente.setDataHoraAtualizacao(LocalDateTime.now());
    }

    return alterado;
  }

  private void criarGasto(Folha folha, Gasto gastoDTO) {
    Gasto novoGasto = new Gasto();
    novoGasto.setDescricao(gastoDTO.getDescricao());
    novoGasto.setCategoria(gastoDTO.getCategoria());
    novoGasto.setValor(gastoDTO.getValor());
    novoGasto.setFolha(folha);
    novoGasto.setUsuario(folha.getUsuario());
    novoGasto.setDataHoraAtualizacao(LocalDateTime.now());
    novoGasto.setDataHoraCriacao(LocalDateTime.now());
    novoGasto.setTipo(Tipo.MENSAL.getDescricao());

    // Salva o novo gasto no repositório
    gastoRepository.save(novoGasto);
  }

  private void removerGastosNaoCorrespondentes(
      List<Gasto> gastosExistentes, List<Gasto> novosGastos) {
    Set<UUID> novosGastosIds =
        novosGastos.stream()
            .map(Gasto::getId)
            .filter(Objects::nonNull) // Filtra IDs não nulos
            .collect(Collectors.toSet());

    for (Gasto gastoExistente : gastosExistentes) {
      if (!novosGastosIds.contains(gastoExistente.getId())) {
        gastoRepository.delete(gastoExistente); // Remove se não houver correspondência
      }
    }
  }

  @Override
  public void excluirGastos(UUID id) {
    executarComandoComTratamentoSemRetornoComMensagem(
        () -> {
          List<Gasto> gastos = gastoRepository.findByFolhaId(id);
          for (Gasto gasto : gastos) {
            gasto.setDataHoraExclusao(LocalDateTime.now()); // Atualiza a data de exclusão
            gastoRepository.save(gasto); // Salva o gasto atualizado
          }
        },
        "Erro ao excluir gastos da folha");
  }
}
