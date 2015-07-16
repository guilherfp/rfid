package br.com.devsource.rfid.bri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devsource.rfid.AbstractRfid;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.RfidDevice;
import br.com.devsource.rfid.RfidModule;

/**
 * @author Guilherme Pacheco
 */
public class BRIReader extends AbstractRfid implements RfidModule {

  private static final String CMD_OK = "OK>";
  private String schema = "ANT,EPCID,STR(3:0,32)";
  private static final Logger LOGGER = LoggerFactory.getLogger(BRIReader.class);

  public BRIReader(ReaderConfig config) {
    super(config);
  }

  @Override
  public void disconect() {
    // TODO Auton-generated method stub

  }

  @Override
  public void connect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void startReader() {
    // TODO Auto-generated method stub

  }

  @Override
  public void stopReader() {
    // TODO Auto-generated method stub

  }

  @Override
  protected RfidDevice requestDevice() {
    // TODO Auto-generated method stub
    return null;
  }

}
