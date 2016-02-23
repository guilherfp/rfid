package br.com.devsource.rfid.bri;

import br.com.devsource.rfid.GpioStatus;
import br.com.devsource.rfid.Protocol;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.ReaderFactory;
import br.com.devsource.rfid.RfidField;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.SimpleAntennaConfig;
import br.com.devsource.rfid.SimpleReaderConfig;

public class BriTest {

  public static void main(String[] args) throws Exception {
    ReaderConfig config = new SimpleReaderConfig("192.168.1.101", 0, Protocol.BRI);
    config.getAntennas().add(new SimpleAntennaConfig(1, 100));

    RfidModule briReader = ReaderFactory.factory(config);

    briReader.startReader(RfidField.EPCID, RfidField.TAGID, RfidField.ANTENNA);

    briReader.getGpio().addGpiHandler(1, GpioStatus.ON, (numero, status) -> {
      System.out.println("> 1 - ON");
    });
    briReader.getGpio().addGpiHandler(2, GpioStatus.ON, (numero, status) -> {
      System.out.println("> 1 - OFF");
    });
    System.out.println("TEST GPI");
  }
}
