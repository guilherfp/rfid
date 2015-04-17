package br.com.devsource.rfid.leitor;

public enum Protocolo {

  /** Lower Level Reader Protocol */
  LLRP("Lower Level Reader Protocol");

  private Protocolo(String descricao) {
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
