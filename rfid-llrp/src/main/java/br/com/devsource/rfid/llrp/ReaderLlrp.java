package br.com.devsource.rfid.llrp;

import java.util.concurrent.TimeoutException;

import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.DELETE_ROSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC;
import org.llrp.ltk.generated.messages.SET_READER_CONFIG;
import org.llrp.ltk.generated.messages.START_ROSPEC;
import org.llrp.ltk.generated.messages.STOP_ROSPEC;
import org.llrp.ltk.net.LLRPConnectionAttemptFailedException;
import org.llrp.ltk.net.LLRPConnector;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.UnsignedInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devsource.rfid.api.ConnectionState;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.RfidConnectionException;
import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.event.ReadHandler;
import br.com.devsource.rfid.core.ReadEventDispatcher;

/**
 * @author Guilherme Pacheco
 */
public final class ReaderLlrp implements Reader {

  private final ReaderConf conf;
  private final LLRPConnector connector;
  private final ReaderEndPoint endPoint;
  private final Capabilities capabilities;
  private final ReadEventDispatcher dispatcher;
  private ConnectionState state;

  private final UnsignedInteger rospecId = new UnsignedInteger(101);
  private static final Logger LOGGER = LoggerFactory.getLogger(ReaderLlrp.class);

  public ReaderLlrp(ReaderConf conf) {
    state = ConnectionState.DISCONNECTED;
    capabilities = new Capabilities(this);
    dispatcher = new ReadEventDispatcher();
    endPoint = new ReaderEndPoint(this);
    connector = createConneciton(conf);
    this.conf = conf;
  }

  private LLRPConnector createConneciton(ReaderConf conf) {
    if (conf.getPort() == 0) {
      return new LLRPConnector(endPoint, conf.getHostname());
    } else {
      return new LLRPConnector(endPoint, conf.getHostname(), conf.getPort());
    }
  }

  @Override
  public ConnectionState getState() {
    return state;
  }

  @Override
  public void connect() {
    if (state == ConnectionState.DISCONNECTED) {
      try {
        connector.connect();
        capabilities.load();
      } catch (LLRPConnectionAttemptFailedException ex) {
        throw new RfidConnectionException(getConf(), ex);
      }
    }
  }

  @Override
  public void disconnect() {
    try {
      connector.disconnect();
      state = ConnectionState.DISCONNECTED;
    } catch (Exception ex) {
      throw new RfidConnectionException(getConf(), ex);
    }
  }

  @Override
  public void start(ReadCommand command) {
    try {
      connect();
      deleteRoSpec();
      configReader();
      addRoSpec(command);
      enableRoSpec();
      startSpec();
    } catch (TimeoutException ex) {
      throw new RfidConnectionException(conf, ex);
    }
  }

  @Override
  public void stop() {
    try {
      stopSpec();
      deleteRoSpec();
    } catch (TimeoutException ex) {
      throw new RfidConnectionException(conf, ex);
    }
  }

  private void enableRoSpec() throws TimeoutException {
    ENABLE_ROSPEC rospec = new ENABLE_ROSPEC();
    rospec.setROSpecID(getRoSpecId());
    transact(rospec);
  }

  private void deleteRoSpec() throws TimeoutException {
    DELETE_ROSPEC rospec = new DELETE_ROSPEC();
    rospec.setROSpecID(getRoSpecId());
    transact(rospec);
  }

  LLRPMessage transact(LLRPMessage message) throws TimeoutException {
    return connector.transact(message, getConf().getTimeOut());
  }

  private void addRoSpec(ReadCommand command) throws TimeoutException {
    ADD_ROSPEC message = new ADD_ROSPEC();
    message.setROSpec(RoSpecUtils.roSpec(this, command));
    transact(message);
  }

  private void startSpec() throws TimeoutException {
    LOGGER.debug("Reader starting...");
    START_ROSPEC message = new START_ROSPEC();
    message.setROSpecID(getRoSpecId());
    transact(message);
    LOGGER.debug("Reader started!");
  }

  private void stopSpec() throws TimeoutException {
    STOP_ROSPEC message = new STOP_ROSPEC();
    message.setROSpecID(getRoSpecId());
    transact(message);
  }

  private void configReader() throws TimeoutException {
    SET_READER_CONFIG config = new SET_READER_CONFIG();
    config.setResetToFactoryDefault(LlrpUtils.YES);
    AntennaUtils.configAntennas(this, config);
    transact(config);
  }

  Capabilities getCapabilities() {
    return capabilities;
  }

  UnsignedInteger getRoSpecId() {
    return rospecId;
  }

  @Override
  public ReaderConf getConf() {
    return conf;
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
}
