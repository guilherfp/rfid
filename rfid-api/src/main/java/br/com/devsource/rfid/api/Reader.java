package br.com.devsource.rfid.api;

import br.com.devsource.rfid.api.ReadCommand.ReadCommandBuilder;
import br.com.devsource.rfid.api.event.ReadHandler;
import br.com.devsource.rfid.api.tag.ReadTagField;

/**
 * @author Guilherme Pacheco
 */
public interface Reader {

  ConnectionState getState();

  void connect() throws RfidConnectionException;

  void disconnect() throws RfidConnectionException;

  void start(ReadCommand command) throws RfidConnectionException;

  default void start() throws RfidConnectionException {
    ReadCommandBuilder builder = ReadCommandBuilder.create();
    builder.add(ReadTagField.EPCID);
    start(builder.build());
  }

  void stop() throws RfidConnectionException;

  ReaderConf getConf();

  void addHandler(ReadHandler handler);

  void removeHandler(ReadHandler handler);

}
