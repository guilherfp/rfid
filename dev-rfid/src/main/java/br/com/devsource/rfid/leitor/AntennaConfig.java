package br.com.devsource.rfid.leitor;

public interface AntennaConfig extends Comparable<AntennaConfig> {

  int getNumber();

  int getTransmitPower();

  boolean isActive();

}
