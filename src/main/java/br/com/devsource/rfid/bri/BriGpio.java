package br.com.devsource.rfid.bri;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.GPITrigger;
import com.intermec.datacollection.rfid.TriggerEvent;
import com.intermec.datacollection.rfid.TriggerEventListener;

import br.com.devsource.rfid.GpiHandler;
import br.com.devsource.rfid.Gpio;
import br.com.devsource.rfid.GpioStatus;

/**
 * @author Guilherme Pacheco
 */
public class BriGpio implements Gpio, TriggerEventListener {

  private final BRIReader briReader;
  private final Map<GPITrigger, GpiHandler> handlers;
  private static Map<Integer, BiPredicate<Integer, GpioStatus>> STATES;

  private static final Logger LOGGER = LoggerFactory.getLogger(BriGpio.class);

  static {
    STATES = new HashMap<>();
    STATES.put(GPITrigger.GPILineStates.INPUT_1_LOW, build(1, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_1_HIGH, build(1, GpioStatus.OFF));
    STATES.put(GPITrigger.GPILineStates.INPUT_2_LOW, build(2, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_2_HIGH, build(2, GpioStatus.OFF));
    STATES.put(GPITrigger.GPILineStates.INPUT_3_LOW, build(3, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_3_HIGH, build(3, GpioStatus.OFF));
    STATES.put(GPITrigger.GPILineStates.INPUT_4_LOW, build(4, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_4_HIGH, build(4, GpioStatus.OFF));
  }

  public BriGpio(BRIReader briReader) {
    briReader.addTriggerEventListener(this);
    disableAutoPoll(briReader);
    this.briReader = briReader;
    handlers = new HashMap<>();
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
  public void gpo(int numero, GpioStatus status) {
    try {
      briReader.execute(String.format("WRITEGPO %d %S", numero, status.name()));
    } catch (BasicReaderException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void addGpiHandler(int numero, GpioStatus status, GpiHandler handler) {
    try {
      GPITrigger gpiTrigger = gpiTrigger(numero, status);
      add(gpiTrigger, handler);
    } catch (BasicReaderException ex) {
      ex.printStackTrace();
    }
  }

  private GPITrigger gpiTrigger(int numero, GpioStatus status) {
    int state = lineState(numero, status);
    String triggerName = String.valueOf(numero);
    return new GPITrigger(triggerName, state);
  }

  private void add(GPITrigger gpiTrigger, GpiHandler handler) throws BasicReaderException {
    briReader.setGPITrigger(gpiTrigger);
    handlers.put(gpiTrigger, handler);
  }

  public static int lineState(int numero, GpioStatus status) {
    for (Entry<Integer, BiPredicate<Integer, GpioStatus>> entry : STATES.entrySet()) {
      if (entry.getValue().test(numero, status)) {
        return entry.getKey();
      }
    }
    return 0;
  }

  @Override
  public void receivedTriggerEvent(TriggerEvent event) {

    System.out.println("--- TRIGGER RECEIVED ---");
    System.out.println("Name: " + event.getTriggerName());
    System.out.println("State: " + event.getGpioState());
    System.out.println("--- END TRIGGER RECEIVED ---");

    Integer numero = Integer.valueOf(event.getTriggerName());
    GpioStatus status = status(event);
    for (Entry<GPITrigger, GpiHandler> entry : handlers.entrySet()) {
      if (entry.getKey().equals(event.getTriggerName())) {
        entry.getValue().onEvent(numero, status);
      }
    }
  }

  private GpioStatus status(TriggerEvent event) {
    return event.getGpioState() == 1 ? GpioStatus.ON : GpioStatus.OFF;
  }

  private static BiPredicate<Integer, GpioStatus> build(int numero, GpioStatus status) {
    return (i, s) -> i == numero && status == s;
  }

}
