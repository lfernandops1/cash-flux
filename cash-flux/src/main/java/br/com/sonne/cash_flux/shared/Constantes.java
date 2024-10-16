package br.com.sonne.cash_flux.shared;

public interface Constantes {

  interface Util {
    String caracters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    String SEM_DESCRICAO = "SEM DESCRIÇÃO";
    String ESPACO_VAZIO = " ";
    String GET = "get";
  }

  interface Mensagens {
    String ERRO_AO_ATUALIZAR_DADOS_USUARIO = "Ocorreu um erro ao atualizr usuário";
    String FOLHA_NAO_ENCONTRADA = "Folha não encontrada";
    String ERRO_AO_EXCLUIR_FOLHA = "Erro ao excluir folha";
    String EMAIL_OU_SENHA_INVALIDO = "Email ou senha invalidos";
    String GASTO_NAO_ENCONTRADO_COM_ID = "Gasto não encontrado com o ID: ";
    String GASTO_NAO_ENCONTRADO = "Gasto não encontrado";
    String ERRO_AO_EXCLUIR_GASTO_ASSOCIADO_A_FOLHA =
        "Não é permitido atualizar gastos associados a uma folha.";
    String ERRO_AO_EXCLUIR_GASTO_FOLHA = "Erro ao excluir gastos da folha";
    String ERRO_AO_ACESSAR_CAMPO = "Erro ao acessar campo: ";
    String ERRO_AO_TENTAR_CRIAR_USUARIO = "Erro ao tentar criar usuario";
    String EMAIL_INVALIDO = "Email inválido.";
    String TELEFONE_INVALIDO = "Telefone inválido.";
    String ERRO_DURANTE_VERIFICACAO_CAMPOS =
        "Ocorreu um problema durante a verificação de campos "
            + "nulos dos dados da classe %s e do campo %s";
  }

  interface PERMISSOES {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";
  }
}
