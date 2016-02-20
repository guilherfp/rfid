package br.com.devsource.rfid.bri;

import java.io.PrintStream;

import br.com.devsource.rfid.GpioStatus;
import br.com.devsource.rfid.Protocol;
import br.com.devsource.rfid.ReadEvent;
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
    // briReader.addHandler(e -> output(e));

    briReader.getGpio().addGpiHandler(1, GpioStatus.ON, (numero, status) -> {
      System.out.println("> 1 - ON");
    });
    briReader.getGpio().addGpiHandler(2, GpioStatus.ON, (numero, status) -> {
      System.out.println("> 1 - OFF");
    });
    // briReader.getGpio().addGpiHandler(1, GpioStatus.OFF, (numero, status) -> {
    // System.out.printf("GPI OFF 1: %s, %s\n", numero, status);
    // });

    System.out.println("TESTE GPI");
    // Thread.sleep(10000);

    // briReader.getGpio().gpo(1, GpioStatus.ON);
    // Thread.sleep(1000);
    // briReader.getGpio().gpo(1, GpioStatus.OFF);
    // Thread.sleep(1000);
    // briReader.getGpio().gpo(1, GpioStatus.ON);
    // Thread.sleep(1000);
    // briReader.getGpio().gpo(1, GpioStatus.OFF);

    // briReader.stopReader();
    // briReader.disconect();
  }

  private static PrintStream output(ReadEvent e) {
    String fmt = "%S %S %S\n\r";
    return System.out.printf(fmt, e.getTag().getEpc(), e.getTag().getTagId(), e.getAntena());
  }

}
