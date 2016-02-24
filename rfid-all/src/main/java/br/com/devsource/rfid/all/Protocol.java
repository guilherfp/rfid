package br.com.devsource.rfid.all;

/**
 * @author Guilherme Pacheco
 */
public enum Protocol {

  /** Lower Level Reader Protocol */
  LLRP("Lower Level Reader Protocol"),
  /** Basic Reader Interface */
  BRI("Basic Reader Interface");

  private String description;

  private Protocol(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return description;
  }

}
