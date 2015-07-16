package br.com.devsource.rfid;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * @author Guilherme Pacheco
 */
public interface RfidModule {

  ReaderConfig getConfig();

  void disconect();

  void connect();

  void startReader();

  void stopReader();

  void addHandler(RfidHandler handler);

  void removeHandler(RfidHandler handler);

  ReadOnlyBooleanProperty isConnected();

  RfidDevice getDevice();

}
