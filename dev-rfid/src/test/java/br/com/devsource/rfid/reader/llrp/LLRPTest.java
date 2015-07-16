package br.com.devsource.rfid.reader.llrp;

import br.com.devsource.rfid.Protocol;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.ReaderCreator;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.llrp.LLRPReader;

/**
 * @author Guilherme Pacheco
 */
public class LLRPTest {

  public static void main(String[] args) {
    ReaderConfig config = ReaderCreator.simpleReader("10.62.33.100", 0, Protocol.LLRP, 1, 2);
    RfidModule module = new LLRPReader(config);
    module.addHandler(System.out::println);
    module.startReader();
    try {
      for (int i = 1; i <= 10; i++) {
        Thread.sleep(1000);
        System.out.println(">>> Time: " + i + "s...");
      }
      System.out.println("Finalizando leitor");
      module.stopReader();
    } catch (Exception ex) {
      ex.printStackTrace();
      module.stopReader();
    }
  }

}
