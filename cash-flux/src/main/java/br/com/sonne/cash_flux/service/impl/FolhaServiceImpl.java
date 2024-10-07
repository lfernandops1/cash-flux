package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.FolhaRepository;
import br.com.sonne.cash_flux.repository.GastoRepository;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import br.com.sonne.cash_flux.service.FolhaService;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.com.sonne.cash_flux.shared.Constantes.Util.SEM_DESCRICAO;
import static br.com.sonne.cash_flux.shared.util.SecurityUtil.obterUsuarioLogado;

@Service
public class FolhaServiceImpl implements FolhaService {

    @Autowired
    private FolhaRepository folhaRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GastoService gastoService;

    public Folha criarFolha(FolhaRequestDTO folhaDTO) {
        Folha folha = obterFolha(folhaDTO);
        folha = folhaRepository.save(folha);
        gastoService.criarGastosEmFolha(folhaDTO, folha);
        folha =
                folhaRepository
                        .findById(folha.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Folha não encontrada"));

        return folha;
    }

    //    public Folha atualizarFolha(UUID folhaId, List<GastoDTO> gastoDTOs) {
    //        Folha folha =
    //                folhaRepository
    //                        .findById(folhaId)
    //                        .orElseThrow(() -> new ResourceNotFoundException("Folha não
    // encontrada"));
    //        List<Gasto> gastos =
    //                gastoDTOs.stream()
    //                        .map(
    //                                g -> {
    //                                    Gasto gasto = new Gasto();
    //                                    gasto.setDescricao(g.getDescricao());
    //                                    gasto.setValor(g.getValor());
    //                                    gasto.setFolha(folha);
    //                                    gasto.setCategoria(g.getCategoria());
    //                                    Usuario usuario =
    //                                            usuarioRepository
    //                                                    .findById(g.getUsuarioId())
    //                                                    .orElseThrow(
    //                                                            () -> new
    // ResourceNotFoundException("Usuário não encontrado"));
    //                                    gasto.setUsuario(usuario);
    //
    //                                    return gasto;
    //                                })
    //                        .collect(Collectors.toList());
    //        gastoRepository.saveAll(gastos);
    //        folha.setGastos(gastos);
    //        return folhaRepository.save(folha);
    //    }

    public List<Folha> listarTodasFolhasUsuario(UUID idUsuario) {
        List<Folha> folhas = folhaRepository.findByUsuarioId(idUsuario);
        if (folhas != null && !folhas.isEmpty()) {
            for (Folha folha : folhas) {
                if (folha.getDescricao() == null || folha.getDescricao().isEmpty()) {
                    folha.setDescricao(SEM_DESCRICAO);
                }
            }
        }
        return folhas;
    }

    public List<Folha> listarPorTipo(Tipo tipo) {
        List<Folha> folhasFiltradas = new ArrayList<>();
        return folhaRepository.findByTipoOrderByDataHoraAtualizacao(tipo);
    }

    private static Folha obterFolha(FolhaDTO folhaDTO) {
        Folha folha = new Folha();
        Usuario usuario = obterUsuarioLogado();
        folha.setTipo(folhaDTO.getTipo());
        folha.setMes(folha.getTipo()
                .equals(Tipo.FOLHA_AVULSA.getDescricao())
                ? null
                : folhaDTO.getMes());
        folha.setDescricao(folhaDTO.getDescricao());
        folha.setUsuario(usuario);
        folha.setDataHoraAtualizacao(LocalDateTime.now());
        folha.setDataHoraCriacao(LocalDateTime.now());
        folha.setGastos(new ArrayList<>());
        return folha;
    }
}
