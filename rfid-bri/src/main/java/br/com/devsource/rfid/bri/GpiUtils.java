package br.com.devsource.rfid.bri;

import com.intermec.datacollection.rfid.GPITrigger;
import com.intermec.datacollection.rfid.TriggerEvent;

import br.com.devsource.rfid.api.gpio.GpiEvent;
import br.com.devsource.rfid.api.gpio.GpioStatus;

/**
 * @author guilherme.pacheco
 */
public final class GpiUtils {

  private GpiUtils() {
    super();
  }

  public static GpiEvent gpiEvent(int number, TriggerEvent event) {
    GpioStatus status = event.getGpioState() == 1 ? GpioStatus.ON : GpioStatus.OFF;
    return new GpiEvent(number, status);
  }

  public static GPITrigger gpiTrigger(int number, GpioStatus status) {
    int state = GpiStates.lineState(number, status);
    String triggerName = String.valueOf(number);
    return new GPITrigger(triggerName, state);
  }

}
