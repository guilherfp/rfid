package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public interface AntennaConfig extends Comparable<AntennaConfig> {

  int getNumber();

  int getTransmitPower();

  boolean isActive();

}
