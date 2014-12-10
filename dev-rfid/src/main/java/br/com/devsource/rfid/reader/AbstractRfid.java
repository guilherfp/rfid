package br.com.devsource.rfid.reader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.event.ReadEvent;
import br.com.devsource.rfid.event.RfidHandler;
import br.com.devsource.rfid.leitor.Leitor;
import br.com.devsource.rfid.tag.Tag;

public abstract class AbstractRfid implements RfidModule {

  private final Leitor leitor;
  private List<RfidHandler> handlers = new ArrayList<>();

  protected AbstractRfid(Leitor leitor) {
    Validate.notNull(leitor);
    this.leitor = leitor;
  }

  @Override
  public final Leitor leitor() {
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
      rfidHandler.call(this, new ReadEvent(tag, leitor, antena));
    }
  }

}
