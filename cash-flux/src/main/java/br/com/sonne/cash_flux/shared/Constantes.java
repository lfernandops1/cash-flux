package br.com.sonne.cash_flux.shared;

public interface Constantes {

  interface Jwt {
    String CHAVE_ID_USUARIO = "id_usuario";
    String CHAVE_EMAIL_USUARIO = "email_usuario";
    String CHAVE_PERMISSOES = "permissoes";
    String SECRET_KEY = "lOt+6woVex2YWds4l3tquVsW7ddXFMidGrrg3aaZlWI=";
    String authorization = "Authorization";
  }

  interface Permissoes {
    String ADMIN = "ROLE_ADMIN";
    String USER = "ROLE_USER";
    String ANONYMOUS = "ROLE_ANONYMOUS";
    String GERAL = "ROLE_GERAL";
  }

  interface Util {
    String caracters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    int valor = 12;
    String regexTelefone = "^\\d{2}9\\d{8}$";
    String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    String SEM_DESCRICAO = "SEM DESCRIÇÃO";
  }
}
