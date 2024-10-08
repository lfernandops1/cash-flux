package br.com.sonne.cash_flux.service.impl;

import br.com.sonne.cash_flux.config.security.TokenService;
import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import br.com.sonne.cash_flux.shared.DTO.AutenticacaoDTO;
import br.com.sonne.cash_flux.shared.DTO.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AutenticacaoServiceImpl implements UserDetailsService {
  @Autowired private ApplicationContext context;

  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private TokenService tokenService;

  private AuthenticationManager authenticationManager;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return usuarioRepository
        .findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Email ou senha invalido."));
  }

  public ResponseEntity<Object> login(@RequestBody @Valid AutenticacaoDTO data) {
    authenticationManager = context.getBean(AuthenticationManager.class);
    var usernamePassword =
        new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
    var auth = this.authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((Usuario) auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponseDTO(token));
  }
}
