package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.repository.GastoRepository;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.service.UsuarioService;
import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.request.GastoRequestDTO;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import br.com.sonne.cash_flux.shared.parse.GastoParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.sonne.cash_flux.shared.util.ExecutarUtil.executarComandoComTratamentoErroComMensagem;

@Service
public class GastoServiceImpl implements GastoService {

    private final GastoRepository gastoRepository;

    @Autowired
    private UsuarioService usuarioService;

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

    private Gasto obterGasto(GastoRequestDTO gastoRequestDTO) {
        return new GastoParse().toEntity(gastoRequestDTO);
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
            throw new RuntimeException("Gasto não encontrado com o ID: " + id);
        }
        gastoAtualizado.setId(id);
        return gastoRepository.save(gastoAtualizado);
    }

    @Override
    public void deletarGasto(UUID id) {
        if (!gastoRepository.existsById(id)) {
            throw new RuntimeException("Gasto não encontrado com o ID: " + id);
        }
        gastoRepository.deleteById(id);
    }
}
