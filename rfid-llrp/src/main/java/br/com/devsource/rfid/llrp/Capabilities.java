package br.com.devsource.rfid.llrp;

import java.util.concurrent.TimeoutException;

import org.llrp.ltk.generated.enumerations.GetReaderCapabilitiesRequestedData;
import org.llrp.ltk.generated.messages.GET_READER_CAPABILITIES;
import org.llrp.ltk.generated.messages.GET_READER_CAPABILITIES_RESPONSE;
import org.llrp.ltk.generated.parameters.GeneralDeviceCapabilities;
import org.llrp.ltk.generated.parameters.RegulatoryCapabilities;

import br.com.devsource.rfid.api.RfidConnectionException;

/**
 * @author Guilherme Pacheco
 */
class Capabilities {

  private final ReaderLlrp readerLlrp;

  private boolean loaded;
  private int maxTransmitPower;
  private int antennas;
  private int gpis;
  private int gpos;

  public Capabilities(ReaderLlrp readerLlrp) {
    this.readerLlrp = readerLlrp;
  }

  public void load(GET_READER_CAPABILITIES_RESPONSE capabilities) {
    maxTransmitPower = maxTrasmitPower(capabilities);
    antennas = antenas(capabilities);
    gpios(capabilities);
    loaded = true;
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

  private GET_READER_CAPABILITIES_RESPONSE capabilities() throws TimeoutException {
    GET_READER_CAPABILITIES message = new GET_READER_CAPABILITIES();
    int request = GetReaderCapabilitiesRequestedData.All;
    message.setRequestedData(new GetReaderCapabilitiesRequestedData(request));
    return (GET_READER_CAPABILITIES_RESPONSE) readerLlrp.transact(message);
  }

  public void load() {
    try {
      if (!loaded) {
        load(capabilities());
      }
    } catch (TimeoutException ex) {
      throw new RfidConnectionException(readerLlrp.getConf(), ex);
    }
  }

  public int getAntennas() {
    return antennas;
  }

  public int getMaxTransmitPower() {
    return maxTransmitPower;
  }

  public int getGpis() {
    return gpis;
  }

  public int getGpos() {
    return gpos;
  }

}
