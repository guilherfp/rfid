package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public class ReaderException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ReaderException() {
    super();
  }

  public ReaderException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReaderException(String message) {
    super(message);
  }

  public ReaderException(Throwable cause) {
    super(cause);
  }

}
