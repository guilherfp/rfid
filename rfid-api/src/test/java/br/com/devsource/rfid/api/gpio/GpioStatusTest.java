package br.com.devsource.rfid.api.gpio;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Guilherme Pacheco
 */
public class GpioStatusTest {

  @Test
  public void testReverse() throws Exception {
    assertEquals(GpioStatus.ON, GpioStatus.OFF.reverse());
    assertEquals(GpioStatus.OFF, GpioStatus.ON.reverse());
  }

}
