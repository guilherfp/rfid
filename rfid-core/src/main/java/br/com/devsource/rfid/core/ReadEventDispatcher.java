package br.com.devsource.rfid.core;

import java.util.ArrayList;
import java.util.List;

import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.event.ReadHandler;

/**
 * @author Guilherme Pacheco
 */
public class ReadEventDispatcher {

  private final List<ReadHandler> handlers;

  public ReadEventDispatcher() {
    handlers = new ArrayList<>();
  }

  public void onRead(ReadEvent event) {
    handlers.forEach(h -> h.call(event));
  }

  public void add(ReadHandler handler) {
    handlers.add(handler);
  }

  public void remove(ReadHandler handler) {
    handlers.remove(handler);
  }

}
