package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public enum Mode {

  UNIQUE("Leitura única"),
  ALL("Ler tudo");

  private String descricao;

  private Mode(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  @Override
  public String toString() {
    return descricao;
  }

}
