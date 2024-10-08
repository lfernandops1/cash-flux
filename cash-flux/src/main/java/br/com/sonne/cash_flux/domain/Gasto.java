package br.com.sonne.cash_flux.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "gastos")
@NoArgsConstructor
public class Gasto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "categoria")
  private String categoria;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  @JsonIgnore
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "folha_id")
  @JsonIgnore
  private Folha folha;

  private String descricao;
  private Double valor;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_hora_criacao", nullable = false)
  private LocalDateTime dataHoraCriacao;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_hora_atualizacao")
  private LocalDateTime dataHoraAtualizacao;

  @Column(name = "tipo")
  private String tipo;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Gasto gasto = (Gasto) o;
    return Objects.equals(id, gasto.id)
        && categoria == gasto.categoria
        && Objects.equals(usuario, gasto.usuario)
        && Objects.equals(folha, gasto.folha)
        && Objects.equals(descricao, gasto.descricao)
        && Objects.equals(valor, gasto.valor)
        && Objects.equals(dataHoraCriacao, gasto.dataHoraCriacao)
        && Objects.equals(dataHoraAtualizacao, gasto.dataHoraAtualizacao)
        && Objects.equals(tipo, gasto.tipo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        categoria,
        usuario,
        folha,
        descricao,
        valor,
        dataHoraCriacao,
        dataHoraAtualizacao,
        tipo);
  }
}
