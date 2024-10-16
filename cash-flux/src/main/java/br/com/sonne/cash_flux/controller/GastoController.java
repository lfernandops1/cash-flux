package br.com.sonne.cash_flux.controller;

import static br.com.sonne.cash_flux.shared.Constantes.ROTAS.*;

import br.com.sonne.cash_flux.domain.Gasto;
import br.com.sonne.cash_flux.service.GastoService;
import br.com.sonne.cash_flux.shared.DTO.request.GastoAlterarRequest;
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
@RequestMapping(API_GASTOS)
public class GastoController {

  @Autowired private final GastoService gastoService;

  @Autowired private GastoParse gastoParse;

  public GastoController(GastoService gastoService) {
    this.gastoService = gastoService;
  }

  @PostMapping(CRIAR)
  public ResponseEntity<GastoResponseDTO> criarGastoAvulso(
      @Valid @RequestBody GastoRequestDTO gastoRequestDTO) {
    Gasto gasto = gastoService.criarGastoAvulso(gastoParse.toEntity(gastoRequestDTO));
    return new ResponseEntity<>(gastoParse.toResponse(gasto), HttpStatus.CREATED);
  }

  @GetMapping(BUSCAR_POR_ID)
  public ResponseEntity<Gasto> buscarGastoPorId(@PathVariable UUID id) {
    Gasto gasto = gastoService.buscarGastoPorId(id);
    return new ResponseEntity<>(gasto, HttpStatus.OK);
  }

  @GetMapping(BUSCAR)
  public ResponseEntity<List<Gasto>> listarTodosGastosAvulsos() {
    List<Gasto> gastos = gastoService.listarTodosGastosAvulsos();
    return new ResponseEntity<>(gastos, HttpStatus.OK);
  }

  @PutMapping(ATUALIZAR_POR_ID)
  public ResponseEntity<GastoResponseDTO> atualizarGasto(
      @Valid @RequestBody GastoAlterarRequest gastoAlterarRequest, @PathVariable UUID id) {
    Gasto gastoAlterado =
        gastoService.atualizarGasto(gastoParse.alterarRequestToEntity(gastoAlterarRequest), id);
    return new ResponseEntity<>(gastoParse.toResponse(gastoAlterado), HttpStatus.OK);
  }
}
