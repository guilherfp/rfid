package br.com.devsource.rfid;

import br.com.devsource.rfid.event.RfidHandler;

public interface RfidReader extends RfidModule {

  void start();

  void stop();

  void addHandler(RfidHandler event);

  void removeHandler(RfidHandler event);

}
