package br.com.devsource.rfid.bri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.ReaderAttributes;

import br.com.devsource.rfid.api.ConnectionState;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.ReaderGpio;
import br.com.devsource.rfid.api.RfidConnectionException;
import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.event.ReadHandler;
import br.com.devsource.rfid.api.gpio.Gpio;
import br.com.devsource.rfid.core.ReadEventDispatcher;

/**
 * @author Guilherme Pacheco
 */
public final class ReaderBri implements ReaderGpio {

  private final ReadEventDispatcher dispatcher;
  private final BriReaderBuilder builder;
  private ConnectionState state;
  private BriGpio briGpio;

  private static final Logger LOGGER = LoggerFactory.getLogger(ReaderBri.class);

  public ReaderBri(ReaderConf conf) {
    state = ConnectionState.DISCONNECTED;
    dispatcher = new ReadEventDispatcher();
    builder = new BriReaderBuilder(conf);
  }

  private void config(BRIReader reader) {
    reader.attributes.setANTS(BriUtils.ants(getConf()));
    reader.attributes.setTagTypes(ReaderAttributes.TagTypeMask.EPC_CLASS1_G2);
    reader.attributes.setFieldStrengthDB(BriUtils.strength(getConf()));
  }

  @Override
  public ConnectionState getConnectionState() {
    return state;
  }

  @Override
  public void connect() throws RfidConnectionException {
    if (ConnectionState.DISCONNECTED.equals(state)) {
      config(builder.get());
      state = ConnectionState.CONNECTED;
    }
  }

  @Override
  public void disconnect() throws RfidConnectionException {
    builder.ifPresent(reader -> {
      stop();
      reader.dispose();
      state = ConnectionState.DISCONNECTED;
    });
  }

  @Override
  public void start(ReadCommand command) throws RfidConnectionException {
    connect();
    builder.get().addTagEventListener(new TagListener(this, command));
    builder.commands().startReading(command);
  }

  @Override
  public void stop() throws RfidConnectionException {
    builder.commands().stopReadingTags();
    LOGGER.info("Leitura finalizada");
  }

  @Override
  public ReaderConf getConf() {
    return builder.getConf();
  }

  @Override
  public void addHandler(ReadHandler handler) {
    dispatcher.add(handler);
  }

  @Override
  public void removeHandler(ReadHandler handler) {
    dispatcher.remove(handler);
  }

  void onRead(ReadEvent event) {
    dispatcher.onRead(event);
  }

  @Override
  public Gpio getGpio() {
    if (briGpio == null) {
      briGpio = new BriGpio(builder);
    }
    return briGpio;
  }
}
