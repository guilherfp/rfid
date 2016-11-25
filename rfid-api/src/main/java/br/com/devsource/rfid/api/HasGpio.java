package br.com.devsource.rfid.api;

import br.com.devsource.rfid.api.gpio.Gpio;

/**
 * @author Guilherme Pacheco
 */
public interface HasGpio {

  Gpio getGpio();

}
