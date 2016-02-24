package br.com.devsource.rfid.llrp;

import br.com.devsource.rfid.api.tag.TagField;
import br.com.devsource.rfid.api.Connection;
import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.event.ReadHandler;

/**
 * @author Guilherme Pacheco
 */
public final class ReaderLlrp implements Reader {

  public ReaderLlrp(ReaderConf conf) {
  }

  @Override
  public Connection getConnection() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void connect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void disconnect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void start(TagField... fields) {
    // TODO Auto-generated method stub

  }

  @Override
  public void stop() {
    // TODO Auto-generated method stub

  }

  @Override
  public ReaderConf getConf() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addHandler(ReadHandler handler) {
    // TODO Auto-generated method stub

  }

  @Override
  public void eventHandler(ReadHandler handler) {
    // TODO Auto-generated method stub

  }

}
