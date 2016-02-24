package br.com.devsource.rfid.api.module;

/**
 * @author Guilherme Pacheco
 */
public class RfidConnectionException extends RfidException {
  private static final long serialVersionUID = 1L;

  public RfidConnectionException(ReaderConf conf) {
    super(conf, "Error connecting to reader");
  }

  public RfidConnectionException(ReaderConf conf, Throwable throwable) {
    super(conf, "Error connecting to reader", throwable);
  }

  public RfidConnectionException(ReaderConf conf, String message, Throwable throwable) {
    super(conf, message, throwable);
  }

  public RfidConnectionException(ReaderConf conf, String message) {
    super(conf, message);
  }

}
