package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public class SimpleRfidDevice implements RfidDevice {

  public static final RfidDevice NAO_ENCONTRADO = new SimpleRfidDevice(4, 29, 4, 4);
  private int antennas;
  private int maxTransmitPower;
  private int gpis;
  private int gpos;

  SimpleRfidDevice() {
    super();
  }

  public SimpleRfidDevice(int antennas, int maxTransmitPower, int gpis, int gpos) {
    this.antennas = antennas;
    this.maxTransmitPower = maxTransmitPower;
    this.gpis = gpis;
    this.gpos = gpos;
  }

  @Override
  public int getMaxTransmitPower() {
    return maxTransmitPower;
  }

  @Override
  public int getAntennas() {
    return antennas;
  }

  @Override
  public int getGPIs() {
    return gpis;
  }

  @Override
  public int getGPOs() {
    return gpos;
  }

}
