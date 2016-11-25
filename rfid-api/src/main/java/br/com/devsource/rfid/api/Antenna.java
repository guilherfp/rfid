package br.com.devsource.rfid.api;

/**
 * @author Guilherme Pacheco
 */
public interface Antenna {

  int getNumber();

  int getTransmitPower();

  default boolean isActive() {
    return true;
  }

}
