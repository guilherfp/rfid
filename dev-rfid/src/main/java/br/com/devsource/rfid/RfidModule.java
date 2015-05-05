package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
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
