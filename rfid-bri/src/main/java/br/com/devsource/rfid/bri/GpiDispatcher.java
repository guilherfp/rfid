package br.com.devsource.rfid.bri;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.GPITrigger;
import com.intermec.datacollection.rfid.TriggerEvent;

import br.com.devsource.rfid.api.gpio.GpiHandler;
import br.com.devsource.rfid.api.gpio.GpioStatus;

/**
 * @author guilherme.pacheco
 */
public final class GpiDispatcher {

  private final BriReaderBuilder builder;
  private final Map<Integer, GpiHandler> handlers;

  public GpiDispatcher(BriReaderBuilder builder) {
    handlers = new HashMap<>();
    this.builder = builder;
  }

  public void add(int number, GpiHandler handler, GpioStatus status) {
    try {
      GPITrigger trigger = GpiUtils.gpiTrigger(number, status);
      builder.get().setGPITrigger(trigger);
      handlers.put(number, handler);
    } catch (BasicReaderException ex) {
      throw new IllegalArgumentException("Não foi possível adicionar gatliho GPI: " + number);
    }
  }

  public void remove(int number) {
    handlers.remove(number);
  }

  public void call(TriggerEvent event) {
    int number = Integer.valueOf(event.getTriggerName());
    handlers.entrySet().stream().filter(e -> e.getKey() == number).forEach(callTrigger(event));
  }

  public Consumer<Entry<Integer, GpiHandler>> callTrigger(TriggerEvent event) {
    return entry -> entry.getValue().onEvent(GpiUtils.gpiEvent(entry.getKey(), event));
  }

}
