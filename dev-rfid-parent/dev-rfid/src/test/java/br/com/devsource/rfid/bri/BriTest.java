package br.com.devsource.rfid.bri;

import br.com.devsource.rfid.SimpleReaderConfig;
import br.com.devsource.rfid.RfidField;

import java.io.PrintStream;

import br.com.devsource.rfid.Protocol;
import br.com.devsource.rfid.ReadEvent;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.ReaderFactory;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.SimpleAntennaConfig;

public class BriTest {

  public static void main(String[] args) throws Exception {
    ReaderConfig config = new SimpleReaderConfig("192.168.0.100", 0, Protocol.BRI);
    config.getAntennas().add(new SimpleAntennaConfig(1, 100));

    RfidModule briReader = ReaderFactory.factory(config);

    briReader.startReader(RfidField.EPCID, RfidField.TAGID, RfidField.ANTENNA);
    briReader.addHandler(e -> output(e));
    Thread.sleep(1000);

    // briReader.getGpio().gpo(1, GpioStatus.ON);
    // Thread.sleep(1000);
    // briReader.getGpio().gpo(1, GpioStatus.OFF);
    // Thread.sleep(1000);
    // briReader.getGpio().gpo(1, GpioStatus.ON);
    // Thread.sleep(1000);
    // briReader.getGpio().gpo(1, GpioStatus.OFF);

    briReader.stopReader();
  }

  private static PrintStream output(ReadEvent e) {
    String fmt = "%S %S %S\n\r";
    return System.out.printf(fmt, e.getTag().getEpc(), e.getTag().getTagId(), e.getAntena());
  }

}
