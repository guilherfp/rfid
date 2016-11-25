package br.com.devsource.rfid.api.gpio;

/**
 * @author Guilherme Pacheco
 */
@FunctionalInterface
public interface GpiHandler {

  void onEvent(GpiEvent event);

}
