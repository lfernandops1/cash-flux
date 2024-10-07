package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.shared.DTO.request.GastoRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.GastoResponseDTO;
import br.com.sonne.cash_flux.shared.parse.GastoParse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

  @Autowired private final GastoService gastoService;

  @Autowired private GastoParse gastoParse;

  public GastoController(GastoService gastoService) {
    this.gastoService = gastoService;
  }

  @PostMapping("/criar")
  public ResponseEntity<GastoResponseDTO> criarGastoAvulso(
      @Valid @RequestBody GastoRequestDTO gastoRequestDTO) {
    Gasto gasto = gastoService.criarGastoAvulso(gastoParse.toEntity(gastoRequestDTO));
    return new ResponseEntity<>(gastoParse.toResponse(gasto), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Gasto> buscarGastoPorId(@PathVariable UUID id) {
    Gasto gasto = gastoService.buscarGastoPorId(id);
    return new ResponseEntity<>(gasto, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Gasto>> listarGastos() {
    List<Gasto> gastos = gastoService.listarGastos();
    return new ResponseEntity<>(gastos, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Gasto> atualizarGasto(
      @PathVariable UUID id, @RequestBody Gasto gastoAtualizado) {
    Gasto gasto = gastoService.atualizarGasto(id, gastoAtualizado);
    return new ResponseEntity<>(gasto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarGasto(@PathVariable UUID id) {
    gastoService.deletarGasto(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
