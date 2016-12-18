package br.com.devsource.rfid.all.factory;

import br.com.devsource.rfid.all.Protocol;

/**
 * @author guilherme.pacheco
 */
final class ReaderGpioFactories {

  private ReaderBriBuilder readerBriBuilder;

  public ReaderGpioFactories() {
    readerBriBuilder = new ReaderBriBuilder();
  }

  public ReaderGpioBuilder get(Protocol protocol) {
    return readerBriBuilder;
  }

}
