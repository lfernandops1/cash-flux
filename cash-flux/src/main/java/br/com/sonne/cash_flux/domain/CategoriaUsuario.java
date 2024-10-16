package br.com.sonne.cash_flux.domain;

import static br.com.sonne.cash_flux.shared.Constantes.TABELA_COLUNAS.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "categorias_usuarios")
public class CategoriaUsuario {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = CODIGO, nullable = false, unique = true)
  private UUID codigo;

  @Column(name = DESCRICAO, nullable = false)
  private String descricao;

  @ManyToOne
  @JoinColumn(name = USUARIO_ID, nullable = false)
  private Usuario usuario;

  @Column(name = DATA_HORA_CRIACAO, nullable = false)
  private LocalDateTime dataHoraCriacao;

  @Column(name = DATA_HORA_ATUALIZACAO, nullable = false)
  private LocalDateTime dataHoraAtualizacao;

  @Column(name = DATA_HORA_EXCLUSAO)
  private LocalDateTime dataHoraExclusao;

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
    CategoriaUsuario that = (CategoriaUsuario) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
