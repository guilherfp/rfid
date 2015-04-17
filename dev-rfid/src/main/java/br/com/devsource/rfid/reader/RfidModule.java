package br.com.devsource.rfid.reader;

import br.com.devsource.rfid.event.RfidHandler;
import br.com.devsource.rfid.leitor.ReaderConfig;

public interface RfidModule {

  ReaderConfig leitor();

  void startReader();

  void stopReader();

  void addHandler(RfidHandler handler);

  void removeHandler(RfidHandler handler);

  void disconect();

  void connect();

  boolean isConnected();

}
