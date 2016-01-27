package br.com.devsource.rfid.bri;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.ReaderAttributes;
import com.intermec.datacollection.rfid.TagEvent;
import com.intermec.datacollection.rfid.TagEventListener;

import br.com.devsource.rfid.AbstractRfid;
import br.com.devsource.rfid.Gpio;
import br.com.devsource.rfid.Mode;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.RfidDevice;
import br.com.devsource.rfid.RfidModule;
import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public class BriReader extends AbstractRfid implements RfidModule {

  private static final String TCP = "tcp://";
  private BRIReader briReader;

  private static final Logger LOGGER = LoggerFactory.getLogger(BriReader.class);

  public BriReader(ReaderConfig config) throws BasicReaderException {
    super(config);
    briReader = createReader(config);
  }

  private BRIReader createReader(ReaderConfig config) throws BasicReaderException {
    String uri = StringUtils.prependIfMissing(config.getHostname(), TCP);
    if (config.getPort() != 0) {
      return new BRIReader(uri, config.getPort());
    } else {
      return new BRIReader(uri);
    }
  }

  @Override
  public void disconect() {

  }

  @Override
  public void connect() {

  }

  @Override
  public void startReader() {
    try {
      briReader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
      briReader.addTagEventListener(tagListener());
      briReader.startReadingTags(null, "EPCID, TAGID", readOperation());
      LOGGER.info("Leitura iniciada");
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  private int readOperation() {
    if (Mode.ALL.equals(getConfig().getMode())) {
      return BRIReader.TagReportOptions.EVENTALL;
    } else {
      return BRIReader.TagReportOptions.EVENT;
    }
  }

  private TagEventListener tagListener() {
    return event -> {
      onRead(tag(event), 1);
    };
  }

  private Tag tag(TagEvent event) {
    String tagKey = new String(event.getTag().getTagKey());
    String epc = StringUtils.removeStartIgnoreCase(tagKey, "H");
    return new Tag(epc);
  }

  @Override
  public void stopReader() {
    try {
      briReader.stopReadingTags();
      LOGGER.info("Leitura finalizada");
      System.out.println("ACABOU");
    } catch (BasicReaderException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  protected RfidDevice requestDevice() {
    return null;
  }

  @Override
  public Gpio getGpio() {
    return new BriGpio(briReader);
  }

}
