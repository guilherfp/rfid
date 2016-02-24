package br.com.devsource.rfid.llrp;

import br.com.devsource.rfid.api.ConnectionState;
import br.com.devsource.rfid.api.ReadCommand;
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
  public ConnectionState getState() {
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
  public void start(ReadCommand command) {
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
  public void removeHandler(ReadHandler handler) {
    // TODO Auto-generated method stub

  }

}
