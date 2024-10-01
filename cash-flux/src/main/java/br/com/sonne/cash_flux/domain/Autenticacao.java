package br.com.sonne.cash_flux.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "autenticacoes")
public class Autenticacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String login;
    private String senha;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
