package br.com.sonne.cash_flux.shared.DTO;

import java.io.Serializable;
import java.util.Objects;

public class TokenDTO implements Serializable {

    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenDTO tokenDTO = (TokenDTO) o;
        return Objects.equals(token, tokenDTO.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "TokenDTO{" + "token='" + token + '\'' + '}';
    }
}
