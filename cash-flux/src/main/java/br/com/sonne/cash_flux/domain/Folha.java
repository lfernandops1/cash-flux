package br.com.sonne.cash_flux.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "folhas")
public class Folha {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String descricao;

  @Column(name = "mes")
  private String mes;

  @OneToMany(mappedBy = "folha", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonBackReference
  private List<Gasto> gastos;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  @JsonIgnore
  private Usuario usuario;

  @Column(name = "tipo")
  private String tipo;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_hora_criacao", nullable = false)
  private LocalDateTime dataHoraCriacao;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_hora_atualizacao")
  private LocalDateTime dataHoraAtualizacao;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Folha folha = (Folha) o;
    return getId() != null && Objects.equals(getId(), folha.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
