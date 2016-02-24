package br.com.devsource.rfid.bri;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.ReaderAttributes;

import br.com.devsource.rfid.api.Antenna;
import br.com.devsource.rfid.api.ConnectionState;
import br.com.devsource.rfid.api.HasGpio;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.ReaderUtils;
import br.com.devsource.rfid.api.RfidConnectionException;
import br.com.devsource.rfid.api.event.ReadHandler;
import br.com.devsource.rfid.api.gpio.Gpio;

/**
 * @author Guilherme Pacheco
 */
public class ReaderBri implements Reader, HasGpio {

  private final BriReaderBuilder builder;
  private ConnectionState state = ConnectionState.DISCONNECTED;
  private BriGpio briGpio;

  private static final int MAX_POWER = 30;
  private static final Logger LOGGER = LoggerFactory.getLogger(ReaderBri.class);

  public ReaderBri(ReaderConf conf) {
    builder = new BriReaderBuilder(conf);
  }

  @Override
  public ConnectionState getState() {
    return state;
  }

  @Override
  public void connect() throws RfidConnectionException {
    if (state.equals(ConnectionState.DISCONNECTED)) {
      config(builder.get());
      state = ConnectionState.CONNECTED;
    }
  }

  private void config(BRIReader briReader) {
    briReader.attributes.setANTS(antenas());
    briReader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
    briReader.attributes.setFieldStrengthDB(potencias());
  }

  private int[] antenas() {
    Set<Antenna> antennas = builder.getConf().getActivesAntennas();
    int[] antenas = new int[antennas.size()];
    int index = 0;
    for (Antenna antena : antennas) {
      antenas[index] = antena.getNumber();
      index++;
    }
    return antenas;
  }

  private int[] potencias() {
    Set<Antenna> antennas = builder.getConf().getActivesAntennas();
    int[] potencias = new int[antennas.size()];
    int index = 0;
    for (Antenna antena : antennas) {
      potencias[index] = ReaderUtils.scale(antena.getTransmitPower(), MAX_POWER);
      index++;
    }
    return potencias;
  }

  @Override
  public void disconnect() throws RfidConnectionException {
    // TODO Auto-generated method stub

  }

  @Override
  public void start(ReadCommand command) throws RfidConnectionException {
    // TODO Auto-generated method stub

  }

  @Override
  public void stop() throws RfidConnectionException {
    try {
      builder.get().stopReadingTags();
      LOGGER.info("Leitura finalizada");
    } catch (BasicReaderException ex) {
      throw new RfidConnectionException(getConf(), ex);
    }
  }

  @Override
  public ReaderConf getConf() {
    return builder.getConf();
  }

  @Override
  public void addHandler(ReadHandler handler) {
    // TODO Auto-generated method stub

  }

  @Override
  public void eventHandler(ReadHandler handler) {
    // TODO Auto-generated method stub

  }

  @Override
  public Gpio getGpio() {
    if (briGpio == null) {
      briGpio = new BriGpio(builder);
    }
    return briGpio;
  }

}
