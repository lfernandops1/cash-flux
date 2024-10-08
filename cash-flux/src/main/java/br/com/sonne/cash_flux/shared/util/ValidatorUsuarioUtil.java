package br.com.sonne.cash_flux.shared.util;

import br.com.sonne.cash_flux.domain.Usuario;
import br.com.sonne.cash_flux.shared.DTO.request.UsuarioCadastroRequestDTO;
import java.util.regex.Pattern;

public class ValidatorUsuarioUtil {

  private static final String regexTelefone = "^\\d{2}\\d{2}9\\d{8}$";
  private static final Pattern PHONE_PATTERN = Pattern.compile(regexTelefone);

  private static final String regexEmail =
      "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(\\.[A-Za-z]{2,})?$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(regexEmail);

  public static boolean validacaoTelefone(String phone) {
    boolean isValid = phone != null && PHONE_PATTERN.matcher(phone).matches();
    if (!isValid) {
      System.out.println("Telefone inválido: " + phone);
    }
    return isValid;
  }

  public static boolean validacaoEmail(String email) {
    boolean isValid = email != null && EMAIL_PATTERN.matcher(email).matches();
    if (!isValid) {
      System.out.println("Email inválido: " + email);
    }
    return isValid;
  }

  public static void validacaoFormatoEmailETelefone(
      UsuarioCadastroRequestDTO usuarioCadastroRequestDTO) {
    if (!validacaoEmail(usuarioCadastroRequestDTO.getEmail())) {
      throw new IllegalArgumentException("Email inválido.");
    }

    if (!validacaoTelefone(usuarioCadastroRequestDTO.getTelefone())) {
      throw new IllegalArgumentException("Telefone inválido.");
    }
  }

  public static void validacaoFormatoEmailETelefoneParaAtualizar(Usuario usuario) {
    if (usuario.getEmail() != null && !validacaoEmail(usuario.getEmail())) {
      throw new IllegalArgumentException("Email inválido.");
    }

    if (usuario.getTelefone() != null && !validacaoTelefone(usuario.getTelefone())) {
      throw new IllegalArgumentException("Telefone inválido.");
    }
  }
}
