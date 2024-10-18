package br.com.sonne.cash_flux.config.security;

import static br.com.sonne.cash_flux.shared.Constantes.Mensagens.ERRO_ENQUANTO_GERAVA_TOKEN_DE_ACESSO;
import static br.com.sonne.cash_flux.shared.Constantes.PERMISSOES.AUTH;
import static br.com.sonne.cash_flux.shared.Constantes.PERMISSOES.SEGREDO;
import static br.com.sonne.cash_flux.shared.Constantes.Util.GMT;
import static br.com.sonne.cash_flux.shared.Constantes.Util.STRING_VAZIA;

import br.com.sonne.cash_flux.domain.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  public String generateToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SEGREDO);

      return JWT.create()
          .withIssuer(AUTH)
          .withSubject(usuario.getUsername())
          .withExpiresAt(getExpirationDate())
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new RuntimeException(ERRO_ENQUANTO_GERAVA_TOKEN_DE_ACESSO, exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SEGREDO);

      return JWT.require(algorithm).withIssuer(AUTH).build().verify(token).getSubject();
    } catch (JWTVerificationException exception) {
      return STRING_VAZIA;
    }
  }

  private Instant getExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(GMT));
  }
}
