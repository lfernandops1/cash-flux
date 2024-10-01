package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.shared.DTO.TokenDTO;
import br.com.sonne.cash_flux.shared.DTO.request.AutenticacaoRequest;
import br.com.sonne.cash_flux.domain.Autenticacao;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.AutenticacaoRepository;
import br.com.sonne.cash_flux.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

import static br.com.sonne.cash_flux.shared.Constantes.Util.caracters;
import static br.com.sonne.cash_flux.shared.Constantes.Util.valor;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    @Autowired
    private AutenticacaoRepository autenticacaoRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoServiceImpl(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public void criarAutenticacao(Usuario usuario) {

        String senhaAleatoria = gerarSenhaAleatoria();
        System.out.println("Senha aleatória gerada: " + senhaAleatoria);

        Autenticacao autenticacao = new Autenticacao();
        autenticacao.setUsuario(usuario);
        autenticacao.setLogin(usuario.getEmail());
        autenticacao.setSenha(senhaAleatoria);

        autenticacaoRepository.save(autenticacao);
        System.out.println("Autenticação registrada para o usuário: " + usuario.getId());
    }

    private String gerarSenhaAleatoria() {
        StringBuilder senha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < valor; i++) {
            senha.append(caracters.charAt(random.nextInt(caracters.length())));
        }
        return senha.toString();
    }

    @Override
    public TokenDTO autenticar(AutenticacaoRequest autenticacaoRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(autenticacaoRequest.getEmail(), autenticacaoRequest.getSenha());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenDTO(this.tokenProvider.criarToken(authentication));
    }


}
