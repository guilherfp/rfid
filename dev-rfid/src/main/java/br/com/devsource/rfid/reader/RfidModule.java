package br.com.devsource.rfid.reader;

import br.com.devsource.rfid.api.leitor.Leitor;
import br.com.devsource.rfid.event.RfidHandler;

public interface RfidModule {

  Leitor leitor();

  void startReader();

  void stopReader();

  void addHandler(RfidHandler handler);

  void removeHandler(RfidHandler handler);

  void disconect();

  void connect();

  boolean isConnected();

}
