package br.com.devsource.rfid.reader;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.reader.llrp.LLRP;

/**
 * @author Guilherme Pacheco
 */
public class ReaderFactory {

  private ReaderFactory() {
    super();
  }

  public static RfidModule factory(ReaderConfig leitor) {
    Validate.notNull(leitor);
    return new LLRP(leitor);
  }

}
