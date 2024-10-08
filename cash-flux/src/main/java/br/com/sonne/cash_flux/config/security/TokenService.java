package br.com.sonne.cash_flux.config.security;

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

  private final String secret = "segredinho";

  public String generateToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      return JWT.create()
          .withIssuer("auth")
          .withSubject(usuario.getUsername())
          .withExpiresAt(getExpirationDate())
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new RuntimeException("ERROR WHILE GENERATING TOKEN", exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();
    } catch (JWTVerificationException exception) {
      return "";
    }
  }

  private Instant getExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
