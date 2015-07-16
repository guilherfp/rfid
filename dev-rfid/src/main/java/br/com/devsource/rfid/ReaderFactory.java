package br.com.devsource.rfid;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.bri.BRIReader;
import br.com.devsource.rfid.llrp.LLRPReader;

/**
 * @author Guilherme Pacheco
 */
public class ReaderFactory {

  private ReaderFactory() {
    super();
  }

  public static RfidModule factory(ReaderConfig leitor) {
    Validate.notNull(leitor);
    if (Protocol.BRI.equals(leitor.getProtocol())) {
      return new BRIReader(leitor);
    } else {
    return new LLRPReader(leitor);
    }
  }

}
