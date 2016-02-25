package br.com.devsource.rfid.bri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intermec.datacollection.rfid.BRIParserException;
import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;
import com.intermec.datacollection.rfid.ReaderAttributes;

import br.com.devsource.rfid.api.ConnectionState;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.ReaderGpio;
import br.com.devsource.rfid.api.RfidConnectionException;
import br.com.devsource.rfid.api.RfidException;
import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.event.ReadHandler;
import br.com.devsource.rfid.api.gpio.Gpio;
import br.com.devsource.rfid.core.ReadEventDispatcher;

/**
 * @author Guilherme Pacheco
 */
public class ReaderBri implements ReaderGpio {

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
    if (state.equals(ConnectionState.DISCONNECTED)) {
      config(builder.get());
      state = ConnectionState.CONNECTED;
    }
  }

  @Override
  public void disconnect() throws RfidConnectionException {
    builder.ifPresent(r -> {
      stop();
      r.dispose();
      state = ConnectionState.DISCONNECTED;
    });
  }

  @Override
  public void start(ReadCommand command) throws RfidConnectionException {
    try {
      connect();
      builder.get().addTagEventListener(new TagListener(this, command));
      String schema = BriUtils.schema(command);
      int operation = BriUtils.operation(command);
      builder.get().startReadingTags(null, schema, operation);
    } catch (BRIParserException ex) {
      throw new RfidException(getConf(), "Invalid reading schema");
    } catch (BasicReaderException ex) {
      throw new RfidConnectionException(getConf(), ex);
    }
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
