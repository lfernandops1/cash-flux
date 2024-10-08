package br.com.sonne.cash_flux.specification;

import br.com.sonne.cash_flux.domain.Folha;
import br.com.sonne.cash_flux.shared.DTO.FolhaFiltroDTO;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class FolhaFiltroSpecification {

  public static Specification<Folha> comFiltros(FolhaFiltroDTO filtro) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Adicionando filtro por tipo
      if (filtro.getTipo() != null && !filtro.getTipo().isEmpty()) {
        predicates.add(criteriaBuilder.equal(root.get("tipo"), filtro.getTipo()));
      }

      // Adicionando filtro por valor mínimo
      if (filtro.getValorMin() != null) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("valor"), filtro.getValorMin()));
      }

      // Adicionando filtro por valor máximo
      if (filtro.getValorMax() != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("valor"), filtro.getValorMax()));
      }

      // Adicionando filtro por descrição
      if (filtro.getDescricao() != null && !filtro.getDescricao().isEmpty()) {
        predicates.add(
            criteriaBuilder.like(root.get("descricao"), "%" + filtro.getDescricao() + "%"));
      }

      // Adicionando filtro por mes
      if (filtro.getMes() != null && !filtro.getMes().isEmpty()) {
        predicates.add(criteriaBuilder.equal(root.get("mes"), filtro.getMes()));
      }

      // Ordenar por
      if (filtro.getOrdenarPor() != null) {
        if (filtro.isAscendente()) {
          query.orderBy(criteriaBuilder.asc(root.get(filtro.getOrdenarPor())));
        } else {
          query.orderBy(criteriaBuilder.desc(root.get(filtro.getOrdenarPor())));
        }
      }

      // Retornar a conjunção dos predicados
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
