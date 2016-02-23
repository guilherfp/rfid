package br.com.devsource.rfid.bri;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.ReaderAttributes;
import br.com.devsource.rfid.AbstractRfid;
import br.com.devsource.rfid.AntennaConfig;
import br.com.devsource.rfid.Gpio;
import br.com.devsource.rfid.Mode;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.ReaderUtils;
import br.com.devsource.rfid.RfidDevice;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.RfidField;
import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public final class BriReader extends AbstractRfid implements RfidModule {

  private static final String TCP = "tcp://";
  private BRIReader briReader;
  private BriGpio briGpio;

  private static final Logger LOGGER = LoggerFactory.getLogger(BriReader.class);

  public BriReader(ReaderConfig config) throws BasicReaderException {
    super(config);
  }

  private BRIReader createReader(ReaderConfig config) throws BasicReaderException {
    String uri = StringUtils.prependIfMissing(config.getHostname(), TCP);
    if (config.getPort() != 0) {
      return new BRIReader(uri, config.getPort());
    } else {
      return new BRIReader(uri);
    }
  }

  private void config(BRIReader briReader) {
    briReader.attributes.setANTS(antenas());
    briReader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
    briReader.attributes.setFieldStrengthDB(potencias());
  }

  private int[] antenas() {
    Set<AntennaConfig> antenasAtivas = antenasAtivas();
    int[] antenas = new int[antenasAtivas.size()];
    int index = 0;
    for (AntennaConfig antena : antenasAtivas) {
      antenas[index] = antena.getNumber();
      index++;
    }
    return antenas;
  }

  private Set<AntennaConfig> antenasAtivas() {
    return getConfig().getActivesAntennas();
  }

  private int[] potencias() {
    Set<AntennaConfig> antenasAtivas = antenasAtivas();
    int[] potencias = new int[antenasAtivas.size()];
    int index = 0;
    for (AntennaConfig antena : antenasAtivas) {
      potencias[index] = ReaderUtils.scale(antena.getTransmitPower(), 30);
      index++;
    }
    return potencias;
  }

  private BRIReader getBriReader() throws BasicReaderException {
    if (briReader == null) {
      briReader = createReader(getConfig());
    }
    return briReader;
  }

  @Override
  public void connect() {
    try {
      if (!isConnected().get()) {
        config(getBriReader());
        setConnected(true);
      }
    } catch (BasicReaderException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void startReader(RfidField... fields) {
    try {
      connect();
      BRIReader briReader = getBriReader();
      briReader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
      briReader.addTagEventListener(new BriTagListener(this, fields));
      briReader.startReadingTags(null, schema(fields), readOperation());
      LOGGER.info("Leitura iniciada");
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private String schema(RfidField[] fields) {
    StringBuilder builder = new StringBuilder();
    Iterator<RfidField> iterator = Arrays.asList(fields).iterator();
    while (iterator.hasNext()) {
      builder.append(iterator.next());
      if (iterator.hasNext()) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }

  private int readOperation() {
    if (Mode.ALL.equals(getConfig().getMode())) {
      return BRIReader.TagReportOptions.EVENTALL;
    } else {
      return BRIReader.TagReportOptions.EVENT;
    }
  }

  void onReadTag(Tag tag, int antena) {
    super.onRead(tag, antena);
  }

  @Override
  public void stopReader() {
    try {
      briReader.stopReadingTags();
      LOGGER.info("Leitura finalizada");
    } catch (BasicReaderException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void disconect() {
    if (briReader != null) {
      briReader.dispose();
      setConnected(false);
    }
  }

  @Override
  protected RfidDevice requestDevice() {
    return null;
  }

  @Override
  public Gpio getGpio() {
    try {
      if (briGpio == null) {
        briGpio = new BriGpio(getBriReader());
      }
      return briGpio;
    } catch (BasicReaderException ex) {
      throw new RuntimeException("Error to connect to Reader");
    }
  }
}
