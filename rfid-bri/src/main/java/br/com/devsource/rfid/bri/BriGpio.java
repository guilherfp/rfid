package br.com.devsource.rfid.bri;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.TriggerEvent;
import com.intermec.datacollection.rfid.TriggerEventListener;

import br.com.devsource.rfid.api.gpio.GpiHandler;
import br.com.devsource.rfid.api.gpio.Gpio;
import br.com.devsource.rfid.api.gpio.GpioStatus;

/**
 * @author Guilherme Pacheco
 */
final class BriGpio implements Gpio, TriggerEventListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(BriGpio.class);

  private final BriReaderBuilder builder;
  private final GpiDispatcher dispatcher;

  public BriGpio(BriReaderBuilder builder) {
    dispatcher = new GpiDispatcher(builder);
    this.builder = builder;
    builder.commands().deleteAllTrigger();
    builder.config(config());
  }

  private Consumer<BRIReader> config() {
    return briReader -> {
      try {
        builder.commands().deleteAllTrigger();
        briReader.setAutoPollTriggerEvents(false);
        briReader.addTriggerEventListener(this);
      } catch (BasicReaderException ex) {
        LOGGER.error(ex.getMessage());
      }
    };
  }

  @Override
  public void gpo(int number, GpioStatus status) {
    builder.commands().gpoWrite(number, status);
  }

  @Override
  public void addGpiHandler(int number, GpioStatus status, GpiHandler handler) {
    dispatcher.add(number, handler, status);
  }

  @Override
  public void receivedTriggerEvent(TriggerEvent event) {
    dispatcher.call(event);
  }

  @Override
  public void removeGpiHandler(int number) {
    dispatcher.remove(number);
  }
}
