package br.com.devsource.rfid.api.event;

/**
 * @author Guilherme Pacheco
 */
@FunctionalInterface
public interface ReadHandler {

  void call(ReadEvent event);

}
