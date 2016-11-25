package br.com.devsource.rfid.llrp;

import java.util.List;

import org.llrp.ltk.generated.messages.SET_READER_CONFIG;
import org.llrp.ltk.generated.parameters.AntennaConfiguration;
import org.llrp.ltk.generated.parameters.RFReceiver;
import org.llrp.ltk.generated.parameters.RFTransmitter;
import org.llrp.ltk.types.UnsignedShort;

import br.com.devsource.rfid.api.Antenna;
import br.com.devsource.rfid.core.ReaderUtils;

/**
 * @author Guilherme Pacheco
 */
class AntennaUtils {

  private static final int MAX_SENSITIVITY = 128;

  public static void configAntennas(ReaderLlrp reader, SET_READER_CONFIG config) {
    List<AntennaConfiguration> list = config.getAntennaConfigurationList();
    reader.getConf().getActivesAntennas().forEach(a -> list.add(antenna(reader, a)));
  }

  private static AntennaConfiguration antenna(ReaderLlrp reader, Antenna antenna) {
    AntennaConfiguration config = new AntennaConfiguration();
    config.setAntennaID(new UnsignedShort(antenna.getNumber()));
    config.setRFTransmitter(AntennaUtils.transmitter(reader.getCapabilities(), antenna));
    config.setRFReceiver(AntennaUtils.receiver());
    return config;
  }

  private static RFReceiver receiver() {
    RFReceiver receiver = new RFReceiver();
    UnsignedShort value = new UnsignedShort(MAX_SENSITIVITY);
    receiver.setReceiverSensitivity(value);
    return receiver;
  }

  private static RFTransmitter transmitter(Capabilities capabilities, Antenna antenna) {
    RFTransmitter transmitter = new RFTransmitter();
    transmitter.setTransmitPower(trasmitPower(capabilities, antenna));
    transmitter.setChannelIndex(new UnsignedShort(0));
    transmitter.setHopTableID(new UnsignedShort(0));
    return transmitter;
  }

  private static UnsignedShort trasmitPower(Capabilities capabilities, Antenna antena) {
    int max = capabilities.getMaxTransmitPower();
    int value = ReaderUtils.scale(antena.getTransmitPower(), max);
    return new UnsignedShort(value);
  }

}
