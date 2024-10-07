package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.service.FolhaService;
import br.com.sonne.cash_flux.shared.DTO.request.FolhaRequestDTO;
import br.com.sonne.cash_flux.shared.DTO.response.FolhaResponseDTO;
import br.com.sonne.cash_flux.shared.enums.Tipo;
import br.com.sonne.cash_flux.shared.parse.FolhaParse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folhas")
public class FolhaController {

  @Autowired private FolhaService folhaService;

  @Autowired private FolhaParse folhaParse;

  public FolhaController(FolhaService folhaService) {
    this.folhaService = folhaService;
  }

  @PostMapping("/criar")
  public ResponseEntity<FolhaResponseDTO> criarFolha(
      @Valid @RequestBody FolhaRequestDTO folhaRequestDTO) {
    Folha novaFolha = folhaService.criarFolha(folhaRequestDTO);
    return new ResponseEntity<>(folhaParse.toResponse(novaFolha), HttpStatus.CREATED);
  }

  @GetMapping("/usuario/{id}")
  public ResponseEntity<List<Folha>> listarFolhasUsuario(@PathVariable("id") UUID idUsuario) {
    List<Folha> folhas = folhaService.listarTodasFolhasUsuario(idUsuario);
    return ResponseEntity.ok((folhas));
  }

  @GetMapping("/usuario/tipo/{tipo}")
  public ResponseEntity<List<Folha>> listarPorTipo(@PathVariable("tipo") Tipo tipo) {
    List<Folha> folhas = folhaService.listarPorTipo(tipo);
    return ResponseEntity.ok((folhas));
  }
}
