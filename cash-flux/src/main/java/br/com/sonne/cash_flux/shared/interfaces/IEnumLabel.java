package br.com.sonne.cash_flux.shared.interfaces;

import br.com.sonne.cash_flux.shared.util.MensagemUtils;

public interface IEnumLabel<E extends Enum<E>> {
  default String getDescricao() {
    return MensagemUtils.getEnumLabel(this);
  }

  default String getDescricao(String[] mensagem) {
    return MensagemUtils.getEnumLabel(this, mensagem);
  }
}
