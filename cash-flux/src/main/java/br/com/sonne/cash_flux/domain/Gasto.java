package br.com.sonne.cash_flux.domain;

import static br.com.sonne.cash_flux.shared.Constantes.TABELA_COLUNAS.*;

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
@Table(name = TABELA_GASTOS)
@NoArgsConstructor
public class Gasto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = CATEGORIA)
  private String categoria;

  @ManyToOne
  @JoinColumn(name = USUARIO_ID)
  @JsonIgnore
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = FOLHA_ID)
  @JsonIgnore
  private Folha folha;

  private String descricao;
  private Double valor;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = DATA_HORA_CRIACAO, nullable = false)
  private LocalDateTime dataHoraCriacao;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = DATA_HORA_ATUALIZACAO)
  private LocalDateTime dataHoraAtualizacao;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = DATA_HORA_EXCLUSAO)
  private LocalDateTime dataHoraExclusao;

  @Column(name = TIPO)
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
