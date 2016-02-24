package br.com.devsource.rfid.bri;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.intermec.datacollection.rfid.GPITrigger;

import br.com.devsource.rfid.api.gpio.GpioStatus;

/**
 * @author Guilherme Pacheco
 */
public class GpiStatesTest {

  @Test
  public void testLineState() throws Exception {
    assertEquals(GPITrigger.GPILineStates.INPUT_1_LOW, GpiStates.lineState(1, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_1_HIGH, GpiStates.lineState(1, GpioStatus.OFF));
    assertEquals(GPITrigger.GPILineStates.INPUT_2_LOW, GpiStates.lineState(2, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_2_HIGH, GpiStates.lineState(2, GpioStatus.OFF));
    assertEquals(GPITrigger.GPILineStates.INPUT_3_LOW, GpiStates.lineState(3, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_3_HIGH, GpiStates.lineState(3, GpioStatus.OFF));
    assertEquals(GPITrigger.GPILineStates.INPUT_4_LOW, GpiStates.lineState(4, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_4_HIGH, GpiStates.lineState(4, GpioStatus.OFF));
  }

}
