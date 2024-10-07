package br.com.sonne.cash_flux.config.exception.handler;

import static java.lang.String.format;

import br.com.sonne.cash_flux.shared.DTO.ValidacaoDTO;
import br.com.sonne.cash_flux.shared.DTO.erro.ErroDTO;
import br.com.sonne.cash_flux.shared.DTO.erro.ErrosDTO;
import br.com.sonne.cash_flux.shared.enums.EValidacao;
import br.com.sonne.cash_flux.shared.util.MensagemUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidacaoMetodoHandler {

  private final String caminhoCampoProperties = ".campo.generico";
  private final char separadorNodes = '.';

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({BindException.class})
  public ErrosDTO bindException(BindException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    List<ValidacaoDTO> validacaoDTOList = new ArrayList<>();

    if (fieldErrors.isEmpty()) {
      result
          .getAllErrors()
          .forEach(
              objectError -> {
                ValidacaoDTO validacaoDTO = new ValidacaoDTO();
                validacaoDTO.setCodes(objectError.getCodes());
                validacaoDTO.setField(objectError.getObjectName());
                validacaoDTOList.add(validacaoDTO);
              });

      return processFieldErrors(validacaoDTOList);
    }

    fieldErrors.forEach(
        fieldError -> {
          ValidacaoDTO validacaoDTO = new ValidacaoDTO();
          validacaoDTO.setCodes(fieldError.getCodes());
          validacaoDTO.setField(fieldError.getField());
          validacaoDTOList.add(validacaoDTO);
        });

    return processFieldErrors(validacaoDTOList);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ErrosDTO methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    List<ValidacaoDTO> validacaoDTOList = new ArrayList<>();

    if (fieldErrors.isEmpty()) {
      result
          .getAllErrors()
          .forEach(
              objectError -> {
                ValidacaoDTO validacaoDTO = new ValidacaoDTO();
                validacaoDTO.setCodes(objectError.getCodes());
                validacaoDTO.setField(objectError.getObjectName());
                validacaoDTOList.add(validacaoDTO);
              });

      return processFieldErrors(validacaoDTOList);
    }

    fieldErrors.forEach(
        fieldError -> {
          ValidacaoDTO validacaoDTO = new ValidacaoDTO();
          validacaoDTO.setCodes(fieldError.getCodes());
          validacaoDTO.setField(fieldError.getField());
          validacaoDTOList.add(validacaoDTO);
        });

    return processFieldErrors(validacaoDTOList);
  }

  private ErrosDTO processFieldErrors(List<ValidacaoDTO> validacaoDTOList) {
    List<ErroDTO> erros = new ArrayList<>();
    final String caminhoObjetoProperties = ".objeto.generico";
    final String caminhoListaProperties = ".lista.generico";
    final int qtdNodesParaObj = 2;
    final String identificadorListas = "[";

    for (ValidacaoDTO validacaoDTO : validacaoDTOList) {
      String caminhoCampo =
          validacaoDTO.getCodes()[0].substring(
              validacaoDTO.getCodes()[0].lastIndexOf(separadorNodes));
      String nomeCampo = caminhoCampo.substring(1, caminhoCampo.length());

      boolean possuiLista = validacaoDTO.getCodes()[0].contains(identificadorListas);
      boolean possuiObjetosEmCastata =
          validacaoDTO.getCodes()[0].chars().filter(ch -> ch == separadorNodes).count()
              > qtdNodesParaObj;

      if (possuiObjetosEmCastata && !possuiLista) {
        preencherErro(erros, validacaoDTO, caminhoCampo, nomeCampo, caminhoObjetoProperties);
        continue;
      }

      if (possuiLista) {
        preencherErro(erros, validacaoDTO, caminhoCampo, nomeCampo, caminhoListaProperties);
        continue;
      }

      ErroDTO erro = new ErroDTO();
      erro.setCodigo(EValidacao.ENTRADA_DE_DADOS_INVALIDA.getCodigo());
      String mensagemProperties =
          obterMensagem(validacaoDTO.getCodes()[0], caminhoCampoProperties, caminhoCampo);
      adicionarErro(erros, erro, format(mensagemProperties, nomeCampo));
    }

    return ErrosDTO.builder().erros(erros).build();
  }

  private void preencherErro(
      List<ErroDTO> erros,
      ValidacaoDTO validacao,
      String caminhoCampo,
      String nomeCampo,
      String caminhoProperties) {
    ErroDTO erro = new ErroDTO();
    erro.setCodigo(EValidacao.ENTRADA_DE_DADOS_INVALIDA.getCodigo());

    String caminhoCampoCompleto = validacao.getField().replaceAll("[^A-Za-z.]", "");
    String nomeGrupo =
        caminhoCampoCompleto.substring(0, caminhoCampoCompleto.indexOf(separadorNodes));

    String mensagemListaProperties =
        obterMensagem(validacao.getCodes()[0], caminhoProperties, caminhoCampo);

    adicionarErro(erros, erro, format(mensagemListaProperties, nomeCampo, nomeGrupo));
  }

  private String obterMensagem(String fieldErro, String caminhoProperties, String caminhoCampo) {
    String parametroProperties =
        fieldErro.substring(0, fieldErro.indexOf(separadorNodes)) + caminhoProperties;
    String mensagemProperties = MensagemUtils.getMensagem(parametroProperties);
    String mensagemPropertiesCompleto = MensagemUtils.getMensagem(fieldErro);

    if (parametroProperties.equals(mensagemProperties)
        && mensagemPropertiesCompleto.equals(fieldErro)) {
      return MensagemUtils.getMensagem(
          fieldErro.substring(0, fieldErro.indexOf(separadorNodes)) + caminhoCampo);
    }

    if (!parametroProperties.equals(mensagemProperties)) return mensagemProperties;

    return mensagemPropertiesCompleto;
  }

  private void adicionarErro(List<ErroDTO> erros, ErroDTO novoErro, String mensagem) {
    novoErro.setMensagem(mensagem);
    log.warn(novoErro.getMensagem());
    erros.add(novoErro);
  }
}
