package br.com.devsource.rfid.all.factory;

import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.ReaderGpio;

/**
 * @author guilherme.pacheco
 */
@FunctionalInterface
public interface ReaderGpioBuilder {

  ReaderGpio readerGpio(ReaderConf readerConf);

}
