package br.com.sonne.cash_flux.service.impl;

import static br.com.sonne.cash_flux.shared.Constantes.Mensagens.*;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;

import br.com.sonne.cash_flux.domain.CategoriaUsuario;
import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.repository.GastoRepository;
import br.com.sonne.cash_flux.service.CategoriaUsuarioService;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.enums.Categoria;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoServiceImpl implements GastoService {

  private final GastoRepository gastoRepository;

  @Autowired private UsuarioService usuarioService;

  @Autowired private CategoriaUsuarioService categoriaUsuarioService;

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
      throw new RuntimeException(GASTO_NAO_ENCONTRADO_COM_ID + id);
    }
  }

  @Override
  public List<Gasto> listarTodosGastosAvulsos() {
    return gastoRepository.findByFolhaIdIsNull();
  }

  @Override
  public Gasto criarGastoAvulso(Gasto gastoParam) {
    return executarComandoComTratamentoErroComMensagem(
        () -> {
          Gasto gasto = new Gasto();
          gasto.setUsuario(usuarioService.carregarUsuarioDaSessao());
          gasto.setDataHoraCriacao(LocalDateTime.now());
          gasto.setDataHoraAtualizacao(LocalDateTime.now());
          gasto.setTipo(Tipo.GASTO_AVULSO.getDescricao().toUpperCase());
          gasto.setValor(gastoParam.getValor());
          gasto.setCategoria(gastoParam.getCategoria().toUpperCase());
          return gastoRepository.save(gasto);
        },
        ERRO_AO_CADASTRAR_GASTO_AVULSO);
  }

  @Override
  public void criarGastosEmFolha(FolhaDTO folhaDTO, Folha folha) {
    if (folhaDTO.getGastos() != null) {
      for (Gasto gastoDTO : folhaDTO.getGastos()) {
        Gasto gasto = new Gasto();
        gasto.setDescricao(gastoDTO.getDescricao());
        gasto.setUsuario(folha.getUsuario());
        gasto.setValor(gastoDTO.getValor());
        gasto.setFolha(folha);
        gasto.setDataHoraAtualizacao(LocalDateTime.now());
        gasto.setDataHoraCriacao(LocalDateTime.now());
        gasto.setTipo(Tipo.MENSAL.getDescricao());

        // Verifica se a descrição corresponde a alguma categoria do Enum
        Optional<Categoria> categoriaGasto =
            encontrarCategoriaPorDescricao(gastoDTO.getCategoria());

        if (categoriaGasto.isPresent()) {
          // Categoria encontrada no Enum, seta a descrição no gasto
          gasto.setCategoria(categoriaGasto.get().getDescricao());
        } else {
          // Caso a categoria não exista no Enum, cria uma nova na tabela Categorias_Usuarios
          CategoriaUsuario novaCategoria =
              this.categoriaUsuarioService.criarNovaCategoria(
                  gastoDTO.getCategoria(), folha.getUsuario());
          gasto.setCategoria(novaCategoria.getDescricao());
        }

        gastoRepository.save(gasto);
      }
    }
  }

  private Optional<Categoria> encontrarCategoriaPorDescricao(String descricao) {
    return Arrays.stream(Categoria.values())
        .filter(categoria -> categoria.getDescricao().equalsIgnoreCase(descricao))
        .findFirst();
  }

  @Override
  public Gasto atualizarGasto(Gasto gasto, UUID id) {
    Gasto gastoExistente =
        gastoRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(GASTO_NAO_ENCONTRADO));
    if (gastoExistente.getFolha() != null) {
      throw new IllegalArgumentException(ERRO_AO_EXCLUIR_GASTO_ASSOCIADO_A_FOLHA);
    }

    gastoExistente.setDescricao(gasto.getDescricao());
    gastoExistente.setValor(gasto.getValor());
    gastoExistente.setCategoria(gasto.getCategoria());
    gastoExistente.setTipo(gasto.getTipo());

    return gastoRepository.save(gastoExistente);
  }

  public void atualizarGastosEmFolha(FolhaRequestDTO folhaDTO, Folha folha) {
    List<Gasto> gastosExistentes = folha.getGastos();
    Map<UUID, Gasto> mapaGastosExistentes =
        gastosExistentes.stream().collect(Collectors.toMap(Gasto::getId, gasto -> gasto));

    // Coletar IDs dos gastos que já existem na folha atual
    Set<UUID> idsGastosNaFolhaAtual = new HashSet<>(mapaGastosExistentes.keySet());

    // Coletar IDs dos gastos que estão sendo enviados no FolhaRequestDTO
    Set<UUID> idsGastosRecebidos =
        folhaDTO.getGastos().stream().map(Gasto::getId).collect(Collectors.toSet());

    // Atualizar ou excluir gastos existentes
    for (UUID idGastoExistente : idsGastosNaFolhaAtual) {
      Gasto gastoAntigo = mapaGastosExistentes.get(idGastoExistente);

      if (!idsGastosRecebidos.contains(idGastoExistente)) {
        // O gasto está na folha atual, mas não no novo DTO, então deve ser excluído
        gastoAntigo.setDataHoraExclusao(LocalDateTime.now());
        gastoAntigo.setDataHoraAtualizacao(LocalDateTime.now());
        gastoRepository.save(gastoAntigo);
        System.out.println("GASTO " + idGastoExistente + " MARCADO COMO EXCLUÍDO");
      } else {
        // O gasto existe tanto na folha atual quanto no novo DTO, vamos atualizá-lo
        Gasto gastoDTO =
            folhaDTO.getGastos().stream()
                .filter(g -> g.getId().equals(idGastoExistente))
                .findFirst()
                .orElse(null);

        if (gastoDTO != null && atualizarGastoSeNecessario(gastoAntigo, gastoDTO)) {
          gastoRepository.save(gastoAntigo);
          System.out.println("GASTO " + idGastoExistente + " ATUALIZADO");
        }
      }
    }

    // Criar novos gastos que não estavam presentes na folha atual
    for (Gasto gastoDTO : folhaDTO.getGastos()) {
      if (!idsGastosNaFolhaAtual.contains(gastoDTO.getId())) {
        criarGasto(folha, gastoDTO);
        System.out.println("NOVO GASTO " + gastoDTO.getId() + " CRIADO");
      }
    }
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
    gastoRepository.save(novoGasto);
  }

  private void removerGastosNaoCorrespondentes(
      List<Gasto> gastosExistentes, List<Gasto> novosGastos) {
    Set<UUID> novosGastosIds =
        novosGastos.stream().map(Gasto::getId).filter(Objects::nonNull).collect(Collectors.toSet());

    for (Gasto gastoExistente : gastosExistentes) {
      if (!novosGastosIds.contains(gastoExistente.getId())) {
        gastoRepository.delete(gastoExistente);
      }
    }
  }

  @Override
  public void excluirGastos(UUID id) {
    executarComandoComTratamentoSemRetornoComMensagem(
        () -> {
          List<Gasto> gastos = gastoRepository.findByFolhaId(id);
          for (Gasto gasto : gastos) {
            gasto.setDataHoraExclusao(LocalDateTime.now());
            gastoRepository.save(gasto);
          }
        },
        ERRO_AO_EXCLUIR_GASTO_FOLHA);
  }
}
