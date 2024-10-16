package br.com.sonne.cash_flux.controller;

import static br.com.sonne.cash_flux.shared.Constantes.ROTAS.*;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.service.FolhaService;
import br.com.sonne.cash_flux.shared.DTO.FolhaFiltroDTO;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.FolhaResponseDTO;
import br.com.sonne.cash_flux.shared.parse.FolhaParse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_FOLHAS)
public class FolhaController {

  @Autowired private FolhaService folhaService;

  @Autowired private FolhaParse folhaParse;

  public FolhaController(FolhaService folhaService) {
    this.folhaService = folhaService;
  }

  @PostMapping(CRIAR)
  public ResponseEntity<FolhaResponseDTO> criarFolha(
      @Valid @RequestBody FolhaRequestDTO folhaRequestDTO) {
    Folha novaFolha = folhaService.criarFolha(folhaRequestDTO);
    return new ResponseEntity<>(folhaParse.toResponse(novaFolha), HttpStatus.CREATED);
  }

  @GetMapping(USUARIO)
  public ResponseEntity<List<Folha>> listarFolhasUsuario() {
    List<Folha> folhas = folhaService.listarTodasFolhasUsuario();
    return ResponseEntity.ok((folhas));
  }

  @PostMapping(FILTRAR_USUARIO)
  public ResponseEntity<List<Folha>> listarPorFiltros(@RequestBody FolhaFiltroDTO filtro) {
    List<Folha> folhas = folhaService.listarPorFiltros(filtro);
    return ResponseEntity.ok(folhas);
  }

  @GetMapping(BUSCAR_FOLHA_POR_ID)
  public ResponseEntity<Folha> buscarFolhaPorId(@PathVariable UUID id) {
    Optional<Folha> folha = folhaService.buscarPorId(id);
    return folha.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping(ALTERAR_FOLHA_POR_ID)
  public ResponseEntity<FolhaResponseDTO> alterarFolha(
      @PathVariable UUID id, @RequestBody FolhaRequestDTO folhaDTO) {
    Folha folhaAlterada = folhaService.alterarFolha(id, folhaDTO);
    return new ResponseEntity<>(folhaParse.toResponse(folhaAlterada), HttpStatus.CREATED);
  }

  @DeleteMapping(EXCLUIR_FOLHA_POR_ID)
  public ResponseEntity<Void> excluirFolha(@PathVariable UUID id) {
    folhaService.excluirFolha(id);
    return ResponseEntity.noContent().build();
  }
}
