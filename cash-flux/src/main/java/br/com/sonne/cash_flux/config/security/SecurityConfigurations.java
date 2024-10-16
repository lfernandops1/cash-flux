package br.com.sonne.cash_flux.config.security;

import static br.com.sonne.cash_flux.shared.Constantes.ROTAS.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  @Autowired SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(API_GASTOS + CRIAR)
                    .authenticated()
                    .requestMatchers(API_FOLHAS + CRIAR)
                    .authenticated()
                    .requestMatchers(API_FOLHAS + USUARIO)
                    .authenticated()
                    .requestMatchers(API_FOLHAS + FILTRAR_USUARIO)
                    .authenticated()
                    .requestMatchers(API_USUARIOS + ATUALIZAR_POR_ID)
                    .authenticated()
                    .requestMatchers(API_USUARIOS + SENHA)
                    .authenticated()
                    .requestMatchers(API_FOLHAS + ALTERAR_FOLHA_POR_ID)
                    .authenticated()
                    .requestMatchers(API_FOLHAS + EXCLUIR_FOLHA_POR_ID)
                    .authenticated()
                    .requestMatchers(API_FOLHAS + BUSCAR_FOLHA_POR_ID)
                    .authenticated()
                    .requestMatchers(API_GASTOS + BUSCAR_POR_ID)
                    .authenticated()
                    .requestMatchers(API_GASTOS + BUSCAR)
                    .authenticated()
                    .requestMatchers(API_GASTOS + ATUALIZAR_POR_ID)
                    .authenticated()
                    .requestMatchers(HttpMethod.POST, API_AUTENTICAR + LOGIN)
                    .permitAll()
                    .requestMatchers(API_USUARIOS + CRIAR)
                    .permitAll()
                    .anyRequest()
                    .permitAll())
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
