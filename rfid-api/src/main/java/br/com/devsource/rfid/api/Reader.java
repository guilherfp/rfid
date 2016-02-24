package br.com.devsource.rfid.api;

import br.com.devsource.rfid.api.event.ReadHandler;
import br.com.devsource.rfid.api.tag.TagField;

/**
 * @author Guilherme Pacheco
 */
public interface Reader {

  Connection getConnection();

  void connect();

  void disconnect();

  void start(TagField... fields);

  void stop();

  ReaderConf getConf();

  void addHandler(ReadHandler handler);

  void eventHandler(ReadHandler handler);

}
