package br.com.sonne.cash_flux.config.exception.handler;

import br.com.sonne.cash_flux.shared.DTO.erro.ErroDTO;
import br.com.sonne.cash_flux.shared.DTO.erro.ErrosDTO;
import br.com.sonne.cash_flux.shared.enums.EValidacao;
import br.com.sonne.cash_flux.shared.util.MensagemUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidacaoParametroHandler {

  @Autowired MensagemUtils MensagemUtils;

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({ConstraintViolationException.class})
  public ErrosDTO classArgumentNotValidException(ConstraintViolationException ex) {
    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
    Set<ErroDTO> erros = new HashSet<>(constraintViolations.size());

    erros.addAll(
        constraintViolations.stream().map(this::criarChaveMensagem).collect(Collectors.toList()));

    return ErrosDTO.builder().erros(new ArrayList<>(erros)).build();
  }

  private ErroDTO criarChaveMensagem(ConstraintViolation constraintViolation) {
    Integer codigo = EValidacao.ENTRADA_DE_DADOS_INVALIDA.getCodigo();
    String mensagem;
    String error =
        constraintViolation
            .getConstraintDescriptor()
            .getAnnotation()
            .annotationType()
            .getSimpleName();
    String nomeParametro = constraintViolation.getPropertyPath().toString();

    if (nomeParametro.contains("[")) {
      mensagem =
          MensagemUtils.getMensagem(
              error + nomeParametro.substring(nomeParametro.lastIndexOf(".")));
      return ErroDTO.builder().codigo(codigo).mensagem(mensagem).build();
    }

    mensagem =
        MensagemUtils.getMensagem(error + nomeParametro.substring(nomeParametro.indexOf(".")));

    log.warn(mensagem);
    return ErroDTO.builder().codigo(codigo).mensagem(mensagem).build();
  }
}
