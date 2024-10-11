package br.com.sonne.cash_flux.config.security;

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
                    .requestMatchers("api/gastos/criar")
                    .authenticated()
                    .requestMatchers("api/folhas/criar")
                    .authenticated()
                    .requestMatchers("api/folhas/usuario")
                    .authenticated()
                    .requestMatchers("api/folhas/usuario/filtrar")
                    .authenticated()
                    .requestMatchers("api/usuarios/atualizar")
                    .authenticated()
                    .requestMatchers("api/usuarios/senha")
                    .authenticated()
                    .requestMatchers("api/folhas/alterar/folha/{id}")
                    .authenticated()
                    .requestMatchers("api/folhas/excluir/folha/{id}")
                    .authenticated()
                    .requestMatchers("api/folhas/buscar/folha/{id}")
                    .authenticated()
                    .requestMatchers("api/gastos/buscar/{id}")
                    .authenticated()
                    .requestMatchers("api/gastos/buscar")
                    .authenticated()
                    .requestMatchers("api/gastos/atualizar/{id}")
                    .authenticated()
                    .requestMatchers(HttpMethod.POST, "/autenticar/login")
                    .permitAll()
                    .requestMatchers("usuarios/criar")
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
