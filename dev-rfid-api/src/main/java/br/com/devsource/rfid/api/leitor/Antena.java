package br.com.devsource.rfid.api.leitor;

public interface Antena extends Comparable<Antena> {

  int numero();

  int potencia();

  boolean ativa();

}
