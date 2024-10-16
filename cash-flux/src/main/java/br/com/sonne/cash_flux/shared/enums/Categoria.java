package br.com.sonne.cash_flux.shared.enums;

import lombok.Getter;

@Getter
public enum Categoria {
  ALIMENTACAO(1, "ALIMENTAÇÃO", " Supermercado, restaurante, delivery"),
  TRANSPORTE(2, "TRANSPORTE", " Gasolina, transporte público, Uber"),
  LAZER(3, "LAZER", " Cinema, shows, jogos, hobbies"),
  SAUDE(4, "SAÚDE", " Remédios, consultas, plano de saúde"),
  EDUCACAO(5, "EDUCAÇÃO", " Cursos, mensalidades, material escolar"),
  MORADIA(6, "MORADIA", " Aluguel, condomínio, contas de água e energia"),
  VESTUARIO_CUIDADOS_PESSOAIS(
      7, "VESTUÁRIO E CUIDADOS PESSOAIS", " Roupas, sapatos, cosméticos, salão de beleza"),
  ASSINATURAS_SERVIÇOS_DIGITAIS(
      8, "ASSINATURAS E SERVIÇOS DIGITAIS", " Netflix, Spotify, serviços de nuvem"),
  IMPOSTOS_TAXAS(9, "IMPOSTOS E TAXAS", " IPTU, IPVA, IRPF, tarifas bancárias"),
  SEGUROS(10, "SEGUROS", " Seguro de vida, automóvel, residência"),
  DIVIDAS_EMPRESTIMOS(
      11, "DÍVIDAS E EMPRÉSTIMOS", " Parcelamentos, pagamento de empréstimos, cartões"),
  DOACOES_CONTRIBUICOES(12, "DOAÇÕES E CONTRIBUIÇÕES", " Doações para ONGs, igreja, crowdfunding"),
  INVESTIMENTOS_POUPANCA(
      13, "INVESTIMENTOS E POUPANÇA", " Poupança, ações, fundos de investimento"),
  PETS(14, "PETS", " Alimentação, veterinário, acessórios"),
  VIAGENS_TURISMO(15, "VIAGENS E TURISMO", " Passagens, hospedagens, pacotes turísticos"),
  MANUTENCAO_REPAROS(16, "MANUTENÇÃO E REPAROS", " Consertos domésticos, manutenção de carro"),
  PRESENTES_FESTIVIDADES(
      17, "PRESENTES E FESTIVIDADES", " Presentes de aniversário, festas de fim de ano"),
  SERVIÇOS_PROFISSIONAIS_CONSULTORIAS(
      18, "SERVIÇOS PROFISSIONAIS E CONSULTORIAS", " Contabilidade, consultas jurídicas, coaching");

  private final int id;
  private final String descricao;
  private final String exemplo;

  Categoria(int id, String descricao, String exemplo) {
    this.id = id;
    this.descricao = descricao;
    this.exemplo = exemplo;
  }

  public static Categoria fromId(int id) {
    for (Categoria categoria : values()) {
      if (categoria.getId() == id) {
        return categoria;
      }
    }
    throw new IllegalArgumentException("ID inválido: " + id);
  }
}
