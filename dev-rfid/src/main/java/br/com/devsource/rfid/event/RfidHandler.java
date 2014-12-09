package br.com.devsource.rfid.event;

import br.com.devsource.rfid.RfidModule;

@FunctionalInterface
public interface RfidHandler {

  void call(RfidModule rfidModule, ReadEvent event);

}
