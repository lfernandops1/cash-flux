package br.com.sonne.cash_flux.domain;

import br.com.sonne.cash_flux.shared.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "usuarios")
public class Usuario implements UserDetails {

  /** Identificador único do usuário. Gerado automaticamente usando UUID. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  /** Nome do usuário. */
  private String nome;

  /** Sobrenome do usuário. */
  private String sobrenome;

  /** Email do usuário, usado como nome de usuário para autenticação. */
  private String email;

  /** Telefone do usuário. */
  private String telefone;

  /** Indica se a conta do usuário está ativa. */
  private Boolean ativo;

  /** Senha do usuário para autenticação. */
  private String senha;

  /**
   * Lista de folhas associadas ao usuário. O relacionamento é unidirecional, com o usuário sendo o
   * "dono" da relação.
   */
  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @ToString.Exclude
  private List<Folha> folhas;

  /**
   * Lista de gastos associados ao usuário. O relacionamento é unidirecional, com o usuário sendo o
   * "dono" da relação.
   */
  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @ToString.Exclude
  private List<Gasto> gastos;

  /**
   * Papel do usuário no sistema (ADMIN ou USER).
   *
   * <p>O campo é enumerado e armazenado como string no banco de dados.
   */
  @Enumerated(EnumType.STRING)
  private Role userRole;

  /**
   * Data e hora de criação do registro do usuário. Este campo não pode ser nulo e não é atualizado
   * após a criação.
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_hora_criacao", nullable = false, updatable = false)
  private LocalDateTime dataHoraCriacao;

  /** Data e hora da última atualização do registro do usuário. Este campo pode ser nulo. */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_hora_atualizacao", nullable = true)
  private LocalDateTime dataHoraAtualizacao;

  /**
   * Construtor personalizado para criar um novo usuário com os dados básicos.
   *
   * @param nome Nome do usuário.
   * @param sobrenome Sobrenome do usuário.
   * @param email Email do usuário.
   * @param telefone Telefone do usuário.
   * @param senha Senha do usuário.
   * @param userRole Papel do usuário (ADMIN ou USER).
   */
  public Usuario(
      String nome, String sobrenome, String email, String telefone, String senha, Role userRole) {
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.telefone = telefone;
    this.senha = senha;
    this.userRole = userRole;
  }

  /**
   * Implementação do método equals para garantir que dois usuários são iguais se e somente se
   * possuem o mesmo identificador (UUID).
   */
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
    Usuario usuario = (Usuario) o;
    return getId() != null && Objects.equals(getId(), usuario.getId());
  }

  /** Sobrescreve o hashCode para garantir a consistência entre as instâncias da entidade. */
  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }

  // Implementações da interface UserDetails

  /**
   * Retorna as autoridades concedidas ao usuário com base no seu papel.
   *
   * @return Uma coleção de {@link GrantedAuthority}, com pelo menos "ROLE_USER". Se o papel do
   *     usuário for ADMIN, também inclui "ROLE_ADMIN".
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.userRole == Role.ADMIN)
      return List.of(
          new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  /**
   * Retorna a senha do usuário.
   *
   * @return A senha do usuário.
   */
  @Override
  public String getPassword() {
    return senha;
  }

  /**
   * Retorna o nome de usuário (neste caso, o email).
   *
   * @return O email do usuário.
   */
  @Override
  public String getUsername() {
    return email;
  }

  /**
   * Indica se a conta do usuário não expirou.
   *
   * @return Sempre retorna true, indicando que a conta não expirou.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Indica se a conta do usuário não está bloqueada.
   *
   * @return Sempre retorna true, indicando que a conta não está bloqueada.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Indica se as credenciais do usuário não expiraram.
   *
   * @return Sempre retorna true, indicando que as credenciais não expiraram.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Indica se o usuário está ativo.
   *
   * @return Sempre retorna true, indicando que o usuário está ativo.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
