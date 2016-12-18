package br.com.devsource.rfid.all.factory;

import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.ReaderGpio;
import br.com.devsource.rfid.bri.ReaderBri;

/**
 * @author guilherme.pacheco
 */
class ReaderBriBuilder implements ReaderBuilder, ReaderGpioBuilder {

  @Override
  public ReaderGpio reader(ReaderConf readerConf) {
    return new ReaderBri(readerConf);
  }

  @Override
  public ReaderGpio readerGpio(ReaderConf readerConf) {
    return new ReaderBri(readerConf);
  }

}
