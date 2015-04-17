package br.com.devsource.rfid.reader;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.leitor.ReaderConfig;
import br.com.devsource.rfid.reader.llrp.LLRP;

public class ReaderFactory {

  public static RfidModule factory(ReaderConfig leitor) {
    Validate.notNull(leitor);
    switch (leitor.protocolo()) {
      case LLRP:
      default:
        return new LLRP(leitor);
    }
  }

}
