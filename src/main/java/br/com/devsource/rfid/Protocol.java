package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public enum Protocol {

  /** Lower Level Reader Protocol */
  LLRP("Lower Level Reader Protocol"),
  /** Basic Reader Interface */
  BRI("Basic Reader Interface");

  private String descricao;

  private Protocol(String descricao) {
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
