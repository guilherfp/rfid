package br.com.devsource.rfid.bri;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
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
  private Map<Integer, GpioStatus> gpios;
  private final Map<GPITrigger, GpiHandler> handlers;
  private static Map<Integer, BiPredicate<Integer, GpioStatus>> STATES;

  static {
    STATES = new HashMap<>();

    STATES.put(GPITrigger.GPILineStates.INPUT_1_HIGH, build(1, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_1_LOW, build(1, GpioStatus.OFF));
    STATES.put(GPITrigger.GPILineStates.INPUT_2_HIGH, build(2, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_2_LOW, build(2, GpioStatus.OFF));
    STATES.put(GPITrigger.GPILineStates.INPUT_3_HIGH, build(3, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_3_LOW, build(3, GpioStatus.OFF));
    STATES.put(GPITrigger.GPILineStates.INPUT_4_HIGH, build(4, GpioStatus.ON));
    STATES.put(GPITrigger.GPILineStates.INPUT_4_LOW, build(4, GpioStatus.OFF));
  }

  private static BiPredicate<Integer, GpioStatus> build(int numero, GpioStatus status) {
    return (i, s) -> i == numero && status == s;
  }

  public BriGpio(BRIReader briReader) {
    briReader.addTriggerEventListener(this);
    this.briReader = briReader;
    handlers = new HashMap<>();
    gpios = new HashMap<>();
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
    gpios.put(numero, status);
    int state = 0;
    for (Entry<Integer, GpioStatus> entry : gpios.entrySet()) {
      state = state | lineState(entry.getKey(), entry.getValue());
    }
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

    System.out.println("------");
    System.out.println("Name: " + event.getTriggerName());
    System.out.println("State: " + event.getGpioState());
    System.out.println("------");

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

}
