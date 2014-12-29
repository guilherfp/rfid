package br.com.devsource.rfid.event;


@FunctionalInterface
public interface RfidHandler {

  void call(ReadEvent event);

}
