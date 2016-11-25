package br.com.devsource.rfid.api;

/**
 * @author Guilherme Pacheco
 */
public enum ReadMode {

  UNIQUE("Event read unique tag"),
  ALL("Event read all tag");

  private String description;

  private ReadMode(String description) {
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
