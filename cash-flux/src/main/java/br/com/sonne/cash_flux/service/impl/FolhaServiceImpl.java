package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.config.exception.ResourceNotFoundException;
import br.com.sonne.cash_flux.repository.FolhaRepository;
import br.com.sonne.cash_flux.repository.GastoRepository;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import br.com.sonne.cash_flux.service.FolhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FolhaServiceImpl implements FolhaService {

    @Autowired
    private FolhaRepository folhaRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Folha criarFolha(FolhaDTO folhaDTO) {
        Folha folha = new Folha();

        folha.setMes(folhaDTO.getMes());

        return folhaRepository.save(folha);
    }

    public Folha atualizarFolha(UUID folhaId, List<GastoDTO> gastoDTOs) {

        // Busca a folha existente ou lança exceção se não for encontrada
        Folha folha = folhaRepository.findById(folhaId)
                .orElseThrow(() -> new ResourceNotFoundException("Folha não encontrada"));

        // Transforma os DTOs de gasto em entidades Gasto
        List<Gasto> gastos = gastoDTOs.stream().map(g -> {
            Gasto gasto = new Gasto();
            gasto.setDescricao(g.getDescricao());
            gasto.setValor(g.getValor());
            gasto.setFolha(folha);  // Define a folha associada a este gasto

            // Define a categoria diretamente, considerando que agora é um enum
            gasto.setCategoria(g.getCategoria());

            // Buscar o usuário por ID
            Usuario usuario = usuarioRepository.findById(g.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            gasto.setUsuario(usuario);

            return gasto;
        }).collect(Collectors.toList());

        // Salva os gastos na tabela de Gasto
        gastoRepository.saveAll(gastos);

        // Atualiza os gastos na folha (após salvar individualmente)
        folha.setGastos(gastos);

        // Salva a folha atualizada com os novos gastos e retorna
        return folhaRepository.save(folha);
    }

    public List<Folha> listarTodasFolhas() {
        return folhaRepository.findAll();
    }
}