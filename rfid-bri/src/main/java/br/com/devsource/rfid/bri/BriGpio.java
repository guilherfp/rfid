package br.com.devsource.rfid.bri;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.GPITrigger;
import com.intermec.datacollection.rfid.TriggerEvent;
import com.intermec.datacollection.rfid.TriggerEventListener;

import br.com.devsource.rfid.api.gpio.GpiEvent;
import br.com.devsource.rfid.api.gpio.GpiHandler;
import br.com.devsource.rfid.api.gpio.Gpio;
import br.com.devsource.rfid.api.gpio.GpioStatus;

/**
 * @author Guilherme Pacheco
 */
class BriGpio implements Gpio, TriggerEventListener {

  private final BriReaderBuilder builder;
  private final Map<Integer, GpiHandler> handlers = new HashMap<>();

  private static final Logger LOGGER = LoggerFactory.getLogger(BriGpio.class);

  public BriGpio(BriReaderBuilder builder) {
    this.builder = builder;
    builder.config(config());
  }

  private Consumer<BRIReader> config() {
    return reader -> {
      disableAutoPoll(reader);
      reader.addTriggerEventListener(this);
    };
  }

  private void disableAutoPoll(BRIReader briReader) {
    try {
      briReader.execute("TRIGGER DELETEALL");
      briReader.setAutoPollTriggerEvents(false);
    } catch (BasicReaderException ex) {
      LOGGER.error(ex.getMessage());
    }
  }

  @Override
  public void gpo(int number, GpioStatus status) {
    try {
      builder.get().execute(String.format("WRITEGPO %d %S", number, status.name()));
    } catch (BasicReaderException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void addGpiHandler(int number, GpioStatus status, GpiHandler handler) {
    try {
      GPITrigger gpiTrigger = gpiTrigger(number, status);
      add(number, gpiTrigger, handler);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private GPITrigger gpiTrigger(int number, GpioStatus status) {
    int state = GpiStates.lineState(number, status);
    String triggerName = String.valueOf(number);
    return new GPITrigger(triggerName, state);
  }

  private void add(int number, GPITrigger trigger, GpiHandler handler) throws BasicReaderException {
    builder.get().setGPITrigger(trigger);
    handlers.put(number, handler);
  }

  @Override
  public void receivedTriggerEvent(TriggerEvent event) {
    Integer number = Integer.valueOf(event.getTriggerName());
    handlers.entrySet().stream().filter(e -> e.getKey() == number).forEach(call(event));
  }

  private Consumer<Entry<Integer, GpiHandler>> call(TriggerEvent event) {
    return entry -> {
      GpiEvent gpiEvent = gpiEvent(entry.getKey(), event);
      entry.getValue().onEvent(gpiEvent);
    };
  }

  private GpiEvent gpiEvent(int number, TriggerEvent event) {
    GpioStatus status = event.getGpioState() == 1 ? GpioStatus.ON : GpioStatus.OFF;
    return new GpiEvent(number, status);
  }

  @Override
  public void removeGpiHandler(int number) {
    handlers.remove(number);
  }
}
