package br.com.devsource.rfid.api.gpio;

/**
 * @author Guilherme Pacheco
 */
public enum GpioStatus {

  ON,
  OFF;

  public GpioStatus reverse() {
    return equals(ON) ? OFF : ON;
  }

}
