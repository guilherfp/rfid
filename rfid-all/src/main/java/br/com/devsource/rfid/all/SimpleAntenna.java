package br.com.devsource.rfid.all;

import br.com.devsource.rfid.api.Antenna;

/**
 * @author Guilherme Pacheco
 */
public class SimpleAntenna implements Antenna {

  private int number;
  private int transmitPower;
  private boolean active;

  public SimpleAntenna() {
    transmitPower = 100;
    active = true;
  }

  @Override
  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  @Override
  public int getTransmitPower() {
    return transmitPower;
  }

  public void setTransmitPower(int transmitPower) {
    this.transmitPower = transmitPower;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

}
