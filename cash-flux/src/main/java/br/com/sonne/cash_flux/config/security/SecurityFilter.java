package br.com.sonne.cash_flux.config.security;

import static br.com.sonne.cash_flux.shared.Constantes.PERMISSOES.AUTHORIZATION;
import static br.com.sonne.cash_flux.shared.Constantes.PERMISSOES.BEARER;
import static br.com.sonne.cash_flux.shared.Constantes.Util.ESPACO_VAZIO;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  @Autowired TokenService tokenService;

  @Autowired UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var token = this.recoverToken(request);

    if (token != null) {
      var email = tokenService.validateToken(token);
      Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
      if (usuarioOptional.isPresent()) {
        Usuario usuario = usuarioOptional.get(); // Desembrulha o Optional para obter o usuário
        var authentication =
            new UsernamePasswordAuthenticationToken(
                usuario, null, usuario.getAuthorities() // Passe o Usuario e suas authorities
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader(AUTHORIZATION);
    if (authHeader == null) return null;
    return authHeader.replace(BEARER, ESPACO_VAZIO);
  }
}
