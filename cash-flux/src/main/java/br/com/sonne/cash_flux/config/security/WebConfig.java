package br.com.sonne.cash_flux.config.security;

import static br.com.sonne.cash_flux.shared.Constantes.Util.ASTERISTICO;
import static br.com.sonne.cash_flux.shared.Constantes.Util.BARRA_ALL;
import static br.com.sonne.cash_flux.shared.Constantes.Web.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping(BARRA_ALL)
        .allowedOrigins(ENDERECO + PORTA_4200)
        .allowedMethods(GET, POST, PUT, DELETE, PATCH, OPTIONS)
        .allowedHeaders(ASTERISTICO)
        .allowCredentials(true);
  }
}
