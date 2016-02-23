package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public enum Mode {

  UNIQUE("Event read unique tag"),
  ALL("Event read all tag");

  private String description;

  private Mode(String description) {
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
