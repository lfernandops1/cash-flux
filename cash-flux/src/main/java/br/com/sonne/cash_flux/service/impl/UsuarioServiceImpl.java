package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.shared.DTO.request.UsuarioRequest;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import br.com.sonne.cash_flux.service.AutenticacaoService;
import br.com.sonne.cash_flux.service.UsuarioService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.sonne.cash_flux.shared.Constantes.Util.regexEmail;
import static br.com.sonne.cash_flux.shared.Constantes.Util.regexTelefone;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;


    @Override
    public Usuario criarUsuario(UsuarioRequest usuarioRequest) throws MessagingException {
        System.out.println("Iniciando criação de usuário...");

        // Validação do email e telefone
        validarEmailETelefone(usuarioRequest);

        // Criação do usuário
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setSobrenome(usuarioRequest.getSobrenome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setTelefone(usuarioRequest.getTelefone());
        usuario.setAtivo(true);

        // Persistindo o usuário no banco de dados
        usuario = usuarioRepository.save(usuario);
        System.out.println("Usuário criado com sucesso: " + usuario);

        // Criando a autenticação após salvar o usuário
        autenticacaoService.criarAutenticacao(usuario);

        return usuario;
    }


    private void validarEmailETelefone(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new IllegalArgumentException("O e-mail fornecido já está em uso.");
        }

        if (usuarioRepository.existsByTelefone(usuarioRequest.getTelefone())) {
            throw new IllegalArgumentException("O telefone fornecido já está em uso.");
        }

        validarTelefone(usuarioRequest.getTelefone());
        validarEmail(usuarioRequest.getEmail());
    }

    private void validarTelefone(String telefone) {
        if (!telefone.matches(regexTelefone)) {
            throw new IllegalArgumentException("O telefone deve ter 11 dígitos no formato DD + 9 + 8 dígitos.");
        }
    }

    private void validarEmail(String email) {
        if (!email.matches(regexEmail)) {
            throw new IllegalArgumentException("O e-mail fornecido não está em um formato válido.");
        }
    }

}
