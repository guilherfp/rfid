package br.com.devsource.rfid;

import br.com.devsource.rfid.tag.Tag;

@FunctionalInterface
public interface RfidEvent {

  void call(RfidModule rfidModule, Tag tag);

}
