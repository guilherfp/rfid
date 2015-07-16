package br.com.devsource.rfid.llrp;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.llrp.ltk.generated.enumerations.AISpecStopTriggerType;
import org.llrp.ltk.generated.enumerations.AirProtocols;
import org.llrp.ltk.generated.enumerations.GetReaderCapabilitiesRequestedData;
import org.llrp.ltk.generated.enumerations.ROReportTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecStartTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecState;
import org.llrp.ltk.generated.enumerations.ROSpecStopTriggerType;
import org.llrp.ltk.generated.interfaces.AirProtocolEPCMemorySelector;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.DELETE_ROSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC;
import org.llrp.ltk.generated.messages.GET_READER_CAPABILITIES;
import org.llrp.ltk.generated.messages.GET_READER_CAPABILITIES_RESPONSE;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.messages.SET_READER_CONFIG;
import org.llrp.ltk.generated.messages.START_ROSPEC;
import org.llrp.ltk.generated.messages.STOP_ROSPEC;
import org.llrp.ltk.generated.parameters.AISpec;
import org.llrp.ltk.generated.parameters.AISpecStopTrigger;
import org.llrp.ltk.generated.parameters.AntennaConfiguration;
import org.llrp.ltk.generated.parameters.C1G2EPCMemorySelector;
import org.llrp.ltk.generated.parameters.InventoryParameterSpec;
import org.llrp.ltk.generated.parameters.RFReceiver;
import org.llrp.ltk.generated.parameters.RFTransmitter;
import org.llrp.ltk.generated.parameters.ROBoundarySpec;
import org.llrp.ltk.generated.parameters.ROReportSpec;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.generated.parameters.ROSpecStartTrigger;
import org.llrp.ltk.generated.parameters.ROSpecStopTrigger;
import org.llrp.ltk.generated.parameters.TagReportContentSelector;
import org.llrp.ltk.net.LLRPConnectionAttemptFailedException;
import org.llrp.ltk.net.LLRPConnector;
import org.llrp.ltk.net.LLRPEndpoint;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.UnsignedByte;
import org.llrp.ltk.types.UnsignedInteger;
import org.llrp.ltk.types.UnsignedShort;
import org.llrp.ltk.types.UnsignedShortArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devsource.rfid.AbstractRfid;
import br.com.devsource.rfid.AntennaConfig;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.ReaderException;
import br.com.devsource.rfid.ReaderUtils;
import br.com.devsource.rfid.RfidDevice;
import br.com.devsource.rfid.SimpleRfidDevice;
import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public final class LLRPReader extends AbstractRfid implements LLRPEndpoint {

  private final LLRPConnector connector;
  private final UnsignedInteger rospecId = new UnsignedInteger(101);
  private static final Logger LOGGER = LoggerFactory.getLogger(LLRPReader.class);

  public LLRPReader(ReaderConfig config) {
    super(config);
    if (config.getPort() == 0) {
      connector = new LLRPConnector(this, config.getHostname());
    } else {
      connector = new LLRPConnector(this, config.getHostname(), config.getPort());
    }
  }

  @Override
  public void connect() {
    try {
      if (!isConnected().get()) {
        LOGGER.info("Iniciando conexão com leitor...");
        connector.connect(2000);
        setConnected(true);
        LOGGER.info("Conexão com leitor iniciada: " + getConfig().toString());
      }
    } catch (LLRPConnectionAttemptFailedException ex) {
      throw new ReaderException("Erro de conexão com leitor", ex);
    }
  }

  @Override
  public void disconect() {
    if (isConnected().get()) {
      connector.disconnect();
      setConnected(false);
    }
  }

  private void enableROSpec() throws TimeoutException {
    ENABLE_ROSPEC rospec = new ENABLE_ROSPEC();
    rospec.setROSpecID(rospecId);
    transact(rospec);
  }

  private void deleteROSpec() throws TimeoutException {
    DELETE_ROSPEC rospec = new DELETE_ROSPEC();
    rospec.setROSpecID(rospecId);
    transact(rospec);
  }

  private UnsignedShortArray getIDAntennas() {
    UnsignedShortArray array = new UnsignedShortArray();
    getAntennas().forEach(a -> array.add(new UnsignedShort(a.getNumber())));
    return array;
  }

  private LLRPMessage transact(LLRPMessage message) throws TimeoutException {
    return connector.transact(message, getConfig().getTimeOut());
  }

  private ROReportSpec getReportSpec() {
    ROReportSpec reportSpec = new ROReportSpec();
    reportSpec.setROReportTrigger(new ROReportTriggerType(
      ROReportTriggerType.Upon_N_Tags_Or_End_Of_ROSpec));
    reportSpec.setN(new UnsignedShort(1));
    reportSpec.setTagReportContentSelector(getTagReportSelector());
    return reportSpec;
  }

  private void addROSpec() throws TimeoutException {
    ROSpec roSpec = new ROSpec();
    roSpec.setROSpecID(rospecId);
    roSpec.setPriority(new UnsignedByte(0));
    roSpec.setCurrentState(new ROSpecState(ROSpecState.Disabled));
    roSpec.setROBoundarySpec(getBoundarySpec());
    roSpec.addToSpecParameterList(getAiSpec());
    roSpec.setROReportSpec(getReportSpec());
    ADD_ROSPEC message = new ADD_ROSPEC();
    message.setROSpec(roSpec);
    transact(message);
  }

  private TagReportContentSelector getTagReportSelector() {
    TagReportContentSelector selector = new TagReportContentSelector();
    selector.setEnableAccessSpecID(LLRPUtils.YES);
    selector.setEnableAntennaID(LLRPUtils.YES);
    selector.setEnableChannelIndex(LLRPUtils.NO);
    selector.setEnableFirstSeenTimestamp(LLRPUtils.NO);
    selector.setEnableInventoryParameterSpecID(LLRPUtils.YES);
    selector.setEnableLastSeenTimestamp(LLRPUtils.NO);
    selector.setEnablePeakRSSI(LLRPUtils.NO);
    selector.setEnableROSpecID(LLRPUtils.NO);
    selector.setEnableSpecIndex(LLRPUtils.NO);
    selector.setEnableTagSeenCount(LLRPUtils.NO);
    List<AirProtocolEPCMemorySelector> airProtocolList =
        selector.getAirProtocolEPCMemorySelectorList();
    C1G2EPCMemorySelector memorySelector = new C1G2EPCMemorySelector();
    memorySelector.setEnableCRC(LLRPUtils.NO);
    memorySelector.setEnablePCBits(LLRPUtils.NO);
    airProtocolList.add(memorySelector);
    selector.setAirProtocolEPCMemorySelectorList(airProtocolList);
    return selector;
  }

  private AISpec getAiSpec() {
    AISpec aiSpec = new AISpec();
    AISpecStopTrigger aiSpecStopTrigger = new AISpecStopTrigger();
    aiSpecStopTrigger
      .setAISpecStopTriggerType(new AISpecStopTriggerType(AISpecStopTriggerType.Null));
    aiSpecStopTrigger.setDurationTrigger(new UnsignedInteger(0));
    aiSpec.setAISpecStopTrigger(aiSpecStopTrigger);
    aiSpec.setAntennaIDs(getIDAntennas());
    aiSpec.addToInventoryParameterSpecList(getInventoryParameter());
    return aiSpec;
  }

  private InventoryParameterSpec getInventoryParameter() {
    InventoryParameterSpec inventoryParameterSpec = new InventoryParameterSpec();
    inventoryParameterSpec.setProtocolID(new AirProtocols(AirProtocols.EPCGlobalClass1Gen2));
    inventoryParameterSpec.setInventoryParameterSpecID(new UnsignedShort(1));
    return inventoryParameterSpec;
  }

  private ROBoundarySpec getBoundarySpec() {
    ROBoundarySpec boundarySpec = new ROBoundarySpec();
    boundarySpec.setROSpecStartTrigger(getStartTrigger());
    boundarySpec.setROSpecStopTrigger(getStopTrigger());
    return boundarySpec;
  }

  private ROSpecStopTrigger getStopTrigger() {
    ROSpecStopTrigger stopTrigger = new ROSpecStopTrigger();
    stopTrigger.setDurationTriggerValue(new UnsignedInteger(0));
    stopTrigger.setROSpecStopTriggerType(new ROSpecStopTriggerType(ROSpecStopTriggerType.Null));
    return stopTrigger;
  }

  private ROSpecStartTrigger getStartTrigger() {
    ROSpecStartTrigger startTrigger = new ROSpecStartTrigger();
    startTrigger.setROSpecStartTriggerType(new ROSpecStartTriggerType(ROSpecStartTriggerType.Null));
    return startTrigger;
  }

  private void startSpec() throws TimeoutException {
    LOGGER.debug("Reader starting...");
    START_ROSPEC message = new START_ROSPEC();
    message.setROSpecID(rospecId);
    transact(message);
    LOGGER.debug("Reader started!");
  }

  private void stopSpec() throws TimeoutException {
    STOP_ROSPEC message = new STOP_ROSPEC();
    message.setROSpecID(rospecId);
    transact(message);
  }

  private AntennaConfiguration configAntena(AntennaConfig antena) {
    AntennaConfiguration config = new AntennaConfiguration();
    config.setAntennaID(new UnsignedShort(antena.getNumber()));
    config.setRFReceiver(getReceiver());
    config.setRFTransmitter(getTransmitter(antena));
    return config;
  }

  private RFReceiver getReceiver() {
    RFReceiver receiver = new RFReceiver();
    receiver.setReceiverSensitivity(sensibilidadeLLRP(128));
    return receiver;
  }

  private RFTransmitter getTransmitter(AntennaConfig antena) {
    RFTransmitter transmitter = new RFTransmitter();
    transmitter.setTransmitPower(trasmitPower(antena));
    transmitter.setChannelIndex(new UnsignedShort(0));
    transmitter.setHopTableID(new UnsignedShort(0));
    return transmitter;
  }

  private UnsignedShort sensibilidadeLLRP(int sensibilidade) {
    return new UnsignedShort(sensibilidade);
  }

  private UnsignedShort trasmitPower(AntennaConfig antena) {
    return new UnsignedShort(power(antena));
  }

  private int power(AntennaConfig antena) {
    int max = getDevice().getMaxTransmitPower();
    return ReaderUtils.scale(antena.getTransmitPower(), max);
  }

  private void configReader() throws TimeoutException {
    SET_READER_CONFIG config = new SET_READER_CONFIG();
    config.setResetToFactoryDefault(LLRPUtils.YES);
    getAntennas().forEach(a -> config.getAntennaConfigurationList().add(configAntena(a)));
    transact(config);
  }

  @Override
  public void startReader() {
    try {
      connect();
      deleteROSpec();
      configReader();
      addROSpec();
      enableROSpec();
      startSpec();
    } catch (TimeoutException ex) {
      throw new ReaderException("Tempo de conexão com o leitor excedido", ex);
    }
  }

  @Override
  public void stopReader() {
    try {
      stopSpec();
      deleteROSpec();
      disconect();
    } catch (TimeoutException ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
  }

  @Override
  public void errorOccured(String errorMessage) {
    LOGGER.error(errorMessage);
  }

  @Override
  public void messageReceived(LLRPMessage message) {
    if (message.getTypeNum() == RO_ACCESS_REPORT.TYPENUM) {
      LLRPUtils.extractedTags(message, this::read);
    }
  }

  private void read(Entry<Tag, Integer> read) {
    onRead(read.getKey(), read.getValue());
  }

  @Override
  public String toString() {
    ReaderConfig config = getConfig();
    String format = String.format("Reader LLRP: %s:%s", config.getHostname(), config.getPort());
    StringBuilder builder = new StringBuilder(format);
    builder.append(" [");
    Iterator<AntennaConfig> antennas = config.getAntennas().iterator();
    while (antennas.hasNext()) {
      AntennaConfig antenna = antennas.next();
      int number = antenna.getNumber();
      int power = power(antenna);
      builder.append(String.format("(%s, %sdB)", number, power));
      if (antennas.hasNext()) {
        builder.append(", ");
      }
    }
    return builder.append("]").toString();
  }

  private GET_READER_CAPABILITIES_RESPONSE getCapabilities() throws TimeoutException {
    GET_READER_CAPABILITIES message = new GET_READER_CAPABILITIES();
    message.setRequestedData(new GetReaderCapabilitiesRequestedData(
      GetReaderCapabilitiesRequestedData.All));
    return (GET_READER_CAPABILITIES_RESPONSE) transact(message);
  }

  @Override
  protected RfidDevice requestDevice() {
    try {
      RfidDeviceBuilder builder = RfidDeviceBuilder.create();
      builder.capabilities(getCapabilities());
      return builder.buid();
    } catch (Exception ex) {
      return SimpleRfidDevice.NAO_ENCONTRADO;
    }
  }
}
