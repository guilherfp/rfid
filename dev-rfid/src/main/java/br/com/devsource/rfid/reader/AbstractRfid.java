package br.com.devsource.rfid.reader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.ReadEvent;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.RfidHandler;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public abstract class AbstractRfid implements RfidModule {

  private final ReaderConfig leitor;
  private List<RfidHandler> handlers = new ArrayList<>();
  private boolean connected = false;

  protected AbstractRfid(ReaderConfig leitor) {
    Validate.notNull(leitor);
    this.leitor = leitor;
  }

  @Override
  public final ReaderConfig leitor() {
    return leitor;
  }

  @Override
  public final void addHandler(RfidHandler handler) {
    if (handler != null) {
      handlers.add(handler);
    }
  }

  @Override
  public final void removeHandler(RfidHandler handler) {
    if (handler != null) {
      handlers.remove(handler);
    }
  }

  protected final void onRead(Tag tag, int antena) {
    for (RfidHandler rfidHandler : handlers) {
      rfidHandler.call(new ReadEvent(tag, leitor, antena));
    }
  }

  protected final void setConnected(boolean connected) {
    this.connected = connected;
  }

  @Override
  public final boolean isConnected() {
    return connected;
  }

}
