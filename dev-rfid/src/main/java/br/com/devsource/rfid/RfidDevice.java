package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public interface RfidDevice {

  int getMaxTransmitPower();

  int getAntennas();

  int getGPIs();

  int getGPOs();

}
