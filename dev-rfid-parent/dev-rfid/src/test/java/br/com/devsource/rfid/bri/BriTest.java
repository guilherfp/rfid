package br.com.devsource.rfid.bri;

import br.com.devsource.rfid.SimpleReaderConfig;
import br.com.devsource.rfid.GpioStatus;
import br.com.devsource.rfid.Protocol;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.ReaderFactory;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.SimpleAntennaConfig;

public class BriTest {

  public static void main(String[] args) throws Exception {
    ReaderConfig config = new SimpleReaderConfig("192.168.0.100", 0, Protocol.BRI);
    config.getAntennas().add(new SimpleAntennaConfig(1, 100));
    RfidModule briReader = ReaderFactory.factory(config);
    // briReader.startReader();
    // briReader.addHandler(e -> System.out.println(e.getTag()));
    briReader.getGpio().gpo(1, GpioStatus.ON);
    Thread.sleep(1000);
    briReader.getGpio().gpo(1, GpioStatus.OFF);
    Thread.sleep(1000);
    briReader.getGpio().gpo(1, GpioStatus.ON);
    Thread.sleep(1000);
    briReader.getGpio().gpo(1, GpioStatus.OFF);
    briReader.stopReader();
  }

}
