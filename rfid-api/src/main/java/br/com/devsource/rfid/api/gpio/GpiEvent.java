package br.com.devsource.rfid.api.gpio;

/**
 * @author Guilherme Pacheco
 */
public class GpiEvent {

  private final int numero;
  private final GpioStatus status;

  public GpiEvent(int numero, GpioStatus status) {
    this.numero = numero;
    this.status = status;
  }

  public int getNumero() {
    return numero;
  }

  public GpioStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return String.format("GpiEvent [numero: %s, status: %s]", numero, status);
  }

}
