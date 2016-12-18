package br.com.devsource.rfid.all.factory;

import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.llrp.ReaderLlrp;

/**
 * @author guilherme.pacheco
 */
public class ReaderLlrpBuilder implements ReaderBuilder {

  @Override
  public Reader reader(ReaderConf readerConf) {
    return new ReaderLlrp(readerConf);
  }

}
