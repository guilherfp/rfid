package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public class SimpleAntena implements AntennaConfig {

  private int number;
  private int transmitPower;
  private boolean active;

  public SimpleAntena(int number, int transmitPower, boolean active) {
    this.number = number;
    this.transmitPower = transmitPower;
    this.active = active;
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public int getTransmitPower() {
    return transmitPower;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public int compareTo(AntennaConfig o) {
    return Integer.compare(number, o.getNumber());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + number;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || !(obj instanceof SimpleAntena)) {
      return false;
    }
    SimpleAntena other = (SimpleAntena) obj;
    return number != other.number;
  }

}
