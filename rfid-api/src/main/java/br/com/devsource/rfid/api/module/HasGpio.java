package br.com.devsource.rfid.api.module;

import br.com.devsource.rfid.api.gpio.Gpio;

/**
 * @author Guilherme Pacheco
 */
public interface HasGpio {

  Gpio getGpio();

}
