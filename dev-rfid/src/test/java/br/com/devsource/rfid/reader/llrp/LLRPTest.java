package br.com.devsource.rfid.reader.llrp;

import br.com.devsource.rfid.leitor.Leitor;
import br.com.devsource.rfid.reader.RfidModule;

public class LLRPTest {

  public static void main(String[] args) {
    Leitor leitor = LLRPCreator.simpleLeitor("10.62.33.100", 0, 1, 2);
    RfidModule module = new LLRP(leitor);
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
