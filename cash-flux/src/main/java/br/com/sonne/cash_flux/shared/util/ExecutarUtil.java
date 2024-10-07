package br.com.sonne.cash_flux.shared.util;

import br.com.sonne.cash_flux.config.exception.CashFluxRuntimeException;
import br.com.sonne.cash_flux.config.exception.ValidacaoException;
import br.com.sonne.cash_flux.config.exception.ValidacaoNotFoundException;
import br.com.sonne.cash_flux.shared.enums.EValidacao;
import br.com.sonne.cash_flux.shared.sample.Parse;
import java.util.List;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutarUtil {

  public static <T> T executarComandoComTratamentoErro(Supplier<T> comando) {
    try {
      return comando.get();
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (RuntimeException e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public static void executarComandoComTratamentoSemRetorno(Runnable comando) {
    try {
      comando.run();
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public static void executarComandoComTratamentoSemRetornoComMensagem(
      Runnable comando, String mensagem) {
    try {
      comando.run();
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.warn(mensagem);
      throw new CashFluxRuntimeException(mensagem);
    }
  }

  public static <T> T executarComandoComTratamentoErroComMensagem(
      Supplier<T> comando, String mensagem) {
    try {
      return comando.get();
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.warn(mensagem);
      throw new CashFluxRuntimeException(mensagem);
    }
  }

  public static <T> T executarComandoComTratamentoErroGenerico(Supplier<T> comando) {
    try {
      return comando.get();
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public static <RequestRequest, Entity, ResponseDTO>
      ResponseDTO executarComandoComTratamentoErroComMensagemComParseResource(
          Supplier<Entity> comando,
          String mensagem,
          Parse<RequestRequest, Entity, ResponseDTO> parse) {
    try {
      Entity entity = comando.get();
      return parse.toResponse(entity);
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(mensagem);
    }
  }

  public static <RequestRequest, Entity, ResponseDTO> ResponseDTO executarComandoComParseResource(
      Supplier<Entity> comando, Parse<RequestRequest, Entity, ResponseDTO> parse) {
    try {
      Entity entity = comando.get();
      return parse.toResponse(entity);
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public static <RequestRequest, Entity, ResponseDTO>
      List<ResponseDTO> executarComandoComTratamentoErroComParseListaResource(
          Supplier<List<Entity>> comando, Parse<RequestRequest, Entity, ResponseDTO> parse) {
    try {
      List<Entity> entities = comando.get();
      return parse.toResponseList(entities);
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  public static <RequestRequest, Entity, ResponseDTO>
      List<ResponseDTO> executarComandoComTratamentoErroComMensagemComParseListaResource(
          Supplier<List<Entity>> comando,
          String mensagem,
          Parse<RequestRequest, Entity, ResponseDTO> parse) {
    try {
      List<Entity> entities = comando.get();
      return parse.toResponseList(entities);
    } catch (ValidacaoException | ValidacaoNotFoundException ex) {
      throw ex;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new CashFluxRuntimeException(mensagem);
    }
  }
}
