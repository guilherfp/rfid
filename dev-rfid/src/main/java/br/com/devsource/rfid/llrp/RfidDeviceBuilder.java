package br.com.devsource.rfid.llrp;

import org.llrp.ltk.generated.messages.GET_READER_CAPABILITIES_RESPONSE;
import org.llrp.ltk.generated.parameters.GeneralDeviceCapabilities;
import org.llrp.ltk.generated.parameters.RegulatoryCapabilities;

import br.com.devsource.rfid.RfidDevice;
import br.com.devsource.rfid.SimpleRfidDevice;

/**
 * @author Guilherme Pacheco
 */
class RfidDeviceBuilder {

  private int antennas;
  private int maxTransmitPower;
  private int gpis;
  private int gpos;

  private RfidDeviceBuilder() {
    super();
  }

  public static RfidDeviceBuilder create() {
    return new RfidDeviceBuilder();
  }

  public void capabilities(GET_READER_CAPABILITIES_RESPONSE capabilities) {
    maxTransmitPower = maxTrasmitPower(capabilities);
    antennas = antenas(capabilities);
    gpios(capabilities);
  }

  private void gpios(GET_READER_CAPABILITIES_RESPONSE capabilities) {
    GeneralDeviceCapabilities general = capabilities.getGeneralDeviceCapabilities();
    gpis = general.getGPIOCapabilities().getNumGPIs().intValue();
    gpos = general.getGPIOCapabilities().getNumGPOs().intValue();
  }

  private int antenas(GET_READER_CAPABILITIES_RESPONSE capabilities) {
    GeneralDeviceCapabilities general = capabilities.getGeneralDeviceCapabilities();
    return general.getMaxNumberOfAntennaSupported().intValue();
  }

  private int maxTrasmitPower(GET_READER_CAPABILITIES_RESPONSE capabilities) {
    RegulatoryCapabilities regulatory = capabilities.getRegulatoryCapabilities();
    return regulatory.getUHFBandCapabilities().getTransmitPowerLevelTableEntryList().size();
  }

  public RfidDevice buid() {
    return new SimpleRfidDevice(antennas, maxTransmitPower, gpis, gpos);
  }

}
