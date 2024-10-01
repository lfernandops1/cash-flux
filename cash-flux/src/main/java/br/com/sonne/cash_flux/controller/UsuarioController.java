package br.com.sonne.cash_flux.controller;

import br.com.sonne.cash_flux.shared.DTO.request.UsuarioRequest;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<Usuario> criarUsuario(
            @RequestBody UsuarioRequest usuarioRequest) {
        try {
            Usuario usuario = usuarioService.criarUsuario(usuarioRequest);
            return ResponseEntity.ok(usuario);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

//    @PostMapping("/verificar")
//    public ResponseEntity<String> verificarCodigo(
//            @RequestParam UUID usuarioId,
//            @RequestParam String codigo) {
//        boolean sucesso = usuarioService.verificarCodigo(usuarioId, codigo);
//        if (sucesso) {
//            return ResponseEntity.ok("Código verificado com sucesso. Usuário autenticado.");
//        } else {
//            return ResponseEntity.status(400).body("Falha na verificação do código. Código inválido ou usuário não encontrado.");
//        }
//    }
}
