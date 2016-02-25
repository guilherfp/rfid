package br.com.devsource.rfid.api.gpio;

/**
 * @author Guilherme Pacheco
 */
public class GpiEvent {

  private final int number;
  private final GpioStatus status;

  public GpiEvent(int number, GpioStatus status) {
    this.number = number;
    this.status = status;
  }

  public int getNumber() {
    return number;
  }

  public GpioStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return String.format("GpiEvent [number: %s, status: %s]", number, status);
  }

}
