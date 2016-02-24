package br.com.devsource.rfid.all;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.bri.ReaderBri;
import br.com.devsource.rfid.llrp.ReaderLlrp;

/**
 * @author Guilherme Pacheco
 */
public class ReaderFactory {

  private ReaderFactory() {
    super();
  }

  public static Reader factory(ReaderConf config, Protocol protocol) {
    Validate.notNull(protocol);
    Validate.notNull(config);
    try {
      if (Protocol.BRI.equals(protocol)) {
        return new ReaderBri(config);
      } else {
        return new ReaderLlrp(config);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

}
