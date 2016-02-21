package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public interface Gpio {

  void gpo(int numero, GpioStatus status);

  void addGpiHandler(int numero, GpioStatus status, GpiHandler handler);

  void removeGpiHandler(int numero);

}
