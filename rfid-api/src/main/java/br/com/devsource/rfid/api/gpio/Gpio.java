package br.com.devsource.rfid.api.gpio;

/**
 * @author Guilherme Pacheco
 */
public interface Gpio {

  void gpo(int number, GpioStatus status);

  void addGpiHandler(int number, GpioStatus status, GpiHandler handler);

  void removeGpiHandler(int number);

}
