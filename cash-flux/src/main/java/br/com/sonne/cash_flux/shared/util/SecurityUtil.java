package br.com.sonne.cash_flux.shared.util;

import br.com.sonne.cash_flux.domain.Usuario;
import java.util.Optional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

  public SecurityUtil() {}

  public static Usuario obterUsuarioLogado() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null
        && authentication.getPrincipal() instanceof UserDetails userDetails) {
      if (userDetails instanceof Usuario) {
        return (Usuario) userDetails;
      }
    }
    return null;
  }

  public static String obterLoginUsuarioLogado() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(authentication -> ((UserDetails) authentication.getPrincipal()).getUsername())
        .orElseThrow(() -> new ServiceException("Erro ao obter dados do usuário."));
  }
}
