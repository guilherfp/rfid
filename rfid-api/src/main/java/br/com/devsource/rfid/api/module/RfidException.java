package br.com.devsource.rfid.api.module;

/**
 * @author Guilherme Pacheco
 */
public class RfidException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private final ReaderConf conf;

  public RfidException(ReaderConf conf) {
    this.conf = conf;
  }

  public RfidException(ReaderConf conf, String message) {
    super(message);
    this.conf = conf;
  }

  public RfidException(ReaderConf conf, Throwable throwable) {
    super(throwable);
    this.conf = conf;
  }

  public RfidException(ReaderConf conf, String message, Throwable throwable) {
    super(message, throwable);
    this.conf = conf;
  }

  public ReaderConf getConf() {
    return conf;
  }

}
