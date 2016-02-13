package br.com.devsource.rfid.bri;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.GPITrigger;
import br.com.devsource.rfid.GpiHandler;
import br.com.devsource.rfid.Gpio;
import br.com.devsource.rfid.GpioStatus;

/**
 * @author Guilherme Pacheco
 */
public class BriGpio implements Gpio {

  private BRIReader briReader;

  public BriGpio(BRIReader briReader) {
    this.briReader = briReader;
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
  public void addGpiHandler(int numero, GpiHandler handler) {
    try {
      briReader.setGPITrigger(new GPITrigger(String.valueOf(numero)));
    } catch (BasicReaderException ex) {
      ex.printStackTrace();
    }
  }

}
