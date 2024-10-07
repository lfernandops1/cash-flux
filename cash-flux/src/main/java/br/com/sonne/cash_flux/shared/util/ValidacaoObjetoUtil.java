package br.com.sonne.cash_flux.shared.util;

import static java.lang.String.format;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidacaoObjetoUtil {

  public boolean verificarTodosCamposNulos(Object dados) {
    Field[] fields = dados.getClass().getDeclaredFields();
    List<Field> fieldsObjeto =
        new java.util.ArrayList<>(Arrays.stream(fields).collect(Collectors.toList()));

    if (Objects.nonNull(dados.getClass().getSuperclass())) {
      List<Field> fieldsHeranca =
          Arrays.stream(dados.getClass().getSuperclass().getDeclaredFields())
              .collect(Collectors.toList());
      fieldsObjeto.addAll(fieldsHeranca);
    }

    for (Field f : fieldsObjeto) {
      try {
        final String metodo = "get";
        String nomePropriedade =
            f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
        Method nomeMetodo = dados.getClass().getMethod(metodo + nomePropriedade);
        Object valorPropriedade = nomeMetodo.invoke(dados);

        if (Objects.nonNull(valorPropriedade)) {
          if (valorPropriedade instanceof String) return false;

          boolean retornoCascata = verificarTodosCamposNulos(valorPropriedade);
          if (!retornoCascata) return false;
        }
      } catch (NoSuchMethodException e) {
        return false;
      } catch (Exception e) {
        log.error(
            format(
                "Ocorreu um problema durante a verificação de campos "
                    + "nulos dos dados da classe %s e do campo %s",
                dados.getClass().getName(), f.getName()),
            e);
        return false;
      }
    }
    return true;
  }
}
