package br.com.devsource.rfid.leitor;

public enum Protocol {

  /** Lower Level Reader Protocol */
  LLRP("Lower Level Reader Protocol");

  private Protocol(String descricao) {
    this.descricao = descricao;
  }

  private String descricao;

  public String getDescricao() {
    return descricao;
  }

  @Override
  public String toString() {
    return descricao;
  }

}
