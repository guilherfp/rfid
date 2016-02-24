package br.com.devsource.rfid.api;

import br.com.devsource.rfid.api.event.ReadHandler;

/**
 * @author Guilherme Pacheco
 */
public interface Reader {

  ConnectionState getState();

  void connect() throws RfidConnectionException;

  void disconnect() throws RfidConnectionException;

  void start(ReadCommand command) throws RfidConnectionException;

  void stop() throws RfidConnectionException;

  ReaderConf getConf();

  void addHandler(ReadHandler handler);

  void eventHandler(ReadHandler handler);

}
