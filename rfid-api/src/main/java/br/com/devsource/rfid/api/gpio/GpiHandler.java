package br.com.devsource.rfid.api.gpio;

@FunctionalInterface
public interface GpiHandler {

  void onEvent(int numero, GpioStatus status);

}
