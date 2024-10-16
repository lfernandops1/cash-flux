package br.com.sonne.cash_flux.shared;

public interface Constantes {

  interface Util {
    String caracters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    String SEM_DESCRICAO = "SEM DESCRIÇÃO";
    String ESPACO_VAZIO = "";
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
    String ERRO_AO_CADASTRAR_GASTO_AVULSO = "Erro ao cadastrar gasto avulso";
  }

  interface PERMISSOES {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";
  }

  interface TABELA_COLUNAS {
    String FOLHA_ID = "folha_id";
    String DATA_HORA_CRIACAO = "data_hora_criacao";
    String DATA_HORA_ATUALIZACAO = "data_hora_atualizacao";
    String DATA_HORA_EXCLUSAO = "data_hora_exclusao";
    String TIPO = "tipo";
    String USUARIO = "usuario";
    String USUARIO_ID = "usuario_id";
    String FOLHA = "folha";
    String MES = "mes";
    String CATEGORIA = "categoria";
    String TABELA_USUARIOS = "usuarios";
    String TABELA_FOLHAS = "folhas";
    String TABELA_GASTOS = "gastos";
    String DESCRICAO = "descricao";
    String CODIGO = "codigo";
  }

  interface ROTAS {
    String LOGIN = "/login";
    String API_AUTENTICAR = "/api/autenticar";
    String API_FOLHAS = "/api/folhas";
    String CRIAR = "/criar";
    String USUARIO = "/usuario";
    String FILTRAR_USUARIO = "/usuario/filtrar";
    String BUSCAR_FOLHA_POR_ID = "buscar/folha/{id}";
    String ALTERAR_FOLHA_POR_ID = "alterar/folha/{id}";
    String EXCLUIR_FOLHA_POR_ID = "excluir/folha/{id}";
    String BUSCAR_POR_ID = "/buscar/{id}";
    String BUSCAR = "/buscar";
    String ATUALIZAR_POR_ID = "/atualizar/{id}";
    String SENHA = "/senha";
    String API_USUARIOS = "/api/usuarios";
    String API_GASTOS = "/api/gastos";
  }

  interface Queries {
    String QUERY_PROCURAR_POR_ID_USUARIO_E_DATA_HORA_EXCLUSAO_NULA =
        "SELECT f FROM Folha f WHERE f.usuario.id = :usuarioId AND f.dataHoraExclusao IS NULL "
            + "ORDER BY CASE WHEN f.dataHoraAtualizacao IS NULL THEN f.dataHoraCriacao ELSE f.dataHoraAtualizacao END ASC";
  }
}
