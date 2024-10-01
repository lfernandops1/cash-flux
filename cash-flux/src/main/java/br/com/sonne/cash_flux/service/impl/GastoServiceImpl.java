package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GastoServiceImpl implements GastoService {

    private final GastoRepository gastoRepository;

    @Autowired
    public GastoServiceImpl(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    @Override
    public Gasto criarGasto(Gasto gasto) {

        gasto.setId(UUID.randomUUID());
        return gastoRepository.save(gasto);
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
