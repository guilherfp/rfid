package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
@FunctionalInterface
public interface RfidHandler {

  void call(ReadEvent event);

}
