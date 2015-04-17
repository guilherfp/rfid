package br.com.devsource.rfid.leitor;

public interface AntennaConfig extends Comparable<AntennaConfig> {

  int numero();

  int potencia();

  boolean ativa();

}
