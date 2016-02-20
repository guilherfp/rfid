package br.com.devsource.rfid.bri;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.intermec.datacollection.rfid.GPITrigger;

import br.com.devsource.rfid.GpioStatus;

/**
 * @author Guilherme Pacheco
 * @author Diego Morais
 */
public class BriGpioTest {

  @Test
  public void testLineState() throws Exception {
    assertEquals(GPITrigger.GPILineStates.INPUT_1_HIGH, BriGpio.lineState(1, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_1_LOW, BriGpio.lineState(1, GpioStatus.OFF));
    assertEquals(GPITrigger.GPILineStates.INPUT_2_HIGH, BriGpio.lineState(2, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_2_LOW, BriGpio.lineState(2, GpioStatus.OFF));
    assertEquals(GPITrigger.GPILineStates.INPUT_3_HIGH, BriGpio.lineState(3, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_3_LOW, BriGpio.lineState(3, GpioStatus.OFF));
    assertEquals(GPITrigger.GPILineStates.INPUT_4_HIGH, BriGpio.lineState(4, GpioStatus.ON));
    assertEquals(GPITrigger.GPILineStates.INPUT_4_LOW, BriGpio.lineState(4, GpioStatus.OFF));
  }

}
