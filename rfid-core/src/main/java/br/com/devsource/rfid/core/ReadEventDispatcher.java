package br.com.devsource.rfid.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.event.ReadHandler;

/**
 * @author Guilherme Pacheco
 */
public class ReadEventDispatcher {

  private final List<ReadHandler> handlers;

  private static final Logger LOGGER = LoggerFactory.getLogger(ReadEventDispatcher.class);

  public ReadEventDispatcher() {
    handlers = new ArrayList<>();
  }

  public void onRead(ReadEvent event) {
    handlers.forEach(h -> {
      try {
        h.call(event);
      } catch (Exception ex) {
        LOGGER.error(ex.getMessage());
      }
    });
  }

  public void add(ReadHandler handler) {
    if (handler != null) {
      handlers.add(handler);
    }
  }

  public void remove(ReadHandler handler) {
    handlers.remove(handler);
  }

}
