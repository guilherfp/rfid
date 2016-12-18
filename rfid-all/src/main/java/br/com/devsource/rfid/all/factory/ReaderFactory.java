package br.com.devsource.rfid.all.factory;

import br.com.devsource.rfid.all.Protocol;

/**
 * @author guilherme.pacheco
 */
public final class ReaderFactory {

  private ReaderFactories readerFactories;
  private ReaderGpioFactories readerGpioFactories;

  public ReaderFactory() {
    readerFactories = new ReaderFactories();
    readerGpioFactories = new ReaderGpioFactories();
  }

  public ReaderBuilder reader(Protocol protocol) {
    return readerFactories.get(protocol);
  }

  public ReaderGpioBuilder readerGpio(Protocol protocol) {
    return readerGpioFactories.get(protocol);
  }

}
