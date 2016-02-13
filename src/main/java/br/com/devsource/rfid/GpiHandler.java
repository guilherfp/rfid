package br.com.devsource.rfid;

@FunctionalInterface
public interface GpiHandler {

  void onEvent(int numero, GpioStatus status);

}
