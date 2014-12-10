package br.com.devsource.rfid.leitor;

public interface Antena extends Comparable<Antena> {

  int numero();

  int potencia();

  boolean ativa();

}
