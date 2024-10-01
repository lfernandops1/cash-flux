package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.shared.DTO.FolhaDTO;
import br.com.sonne.cash_flux.shared.DTO.GastoDTO;
import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.service.FolhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/folhas")
public class FolhaController {

    @Autowired
    private FolhaService folhaService;

    @PostMapping
    public ResponseEntity<Folha> criarFolha(@RequestBody FolhaDTO folhaDTO) {
        Folha novaFolha = folhaService.criarFolha(folhaDTO);
        return ResponseEntity.ok(novaFolha);
    }


    @PutMapping("/{folhaId}")
    public ResponseEntity<Folha> alterarFolha(@PathVariable UUID folhaId, @RequestBody List<GastoDTO> gastoDTOs) {
        Folha folhaAtualizada = folhaService.atualizarFolha(folhaId, gastoDTOs);
        return ResponseEntity.ok(folhaAtualizada);
    }
}
