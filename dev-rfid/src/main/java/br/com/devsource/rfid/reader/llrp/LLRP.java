package br.com.devsource.rfid.reader.llrp;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.llrp.ltk.generated.enumerations.AISpecStopTriggerType;
import org.llrp.ltk.generated.enumerations.AirProtocols;
import org.llrp.ltk.generated.enumerations.ROReportTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecStartTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecState;
import org.llrp.ltk.generated.enumerations.ROSpecStopTriggerType;
import org.llrp.ltk.generated.interfaces.AirProtocolEPCMemorySelector;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.DELETE_ROSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.messages.SET_READER_CONFIG;
import org.llrp.ltk.generated.messages.START_ROSPEC;
import org.llrp.ltk.generated.messages.STOP_ROSPEC;
import org.llrp.ltk.generated.parameters.AISpec;
import org.llrp.ltk.generated.parameters.AISpecStopTrigger;
import org.llrp.ltk.generated.parameters.AntennaConfiguration;
import org.llrp.ltk.generated.parameters.C1G2EPCMemorySelector;
import org.llrp.ltk.generated.parameters.EPC_96;
import org.llrp.ltk.generated.parameters.InventoryParameterSpec;
import org.llrp.ltk.generated.parameters.RFTransmitter;
import org.llrp.ltk.generated.parameters.ROBoundarySpec;
import org.llrp.ltk.generated.parameters.ROReportSpec;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.generated.parameters.ROSpecStartTrigger;
import org.llrp.ltk.generated.parameters.ROSpecStopTrigger;
import org.llrp.ltk.generated.parameters.TagReportContentSelector;
import org.llrp.ltk.generated.parameters.TagReportData;
import org.llrp.ltk.net.LLRPConnectionAttemptFailedException;
import org.llrp.ltk.net.LLRPConnector;
import org.llrp.ltk.net.LLRPEndpoint;
import org.llrp.ltk.types.Bit;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.UnsignedByte;
import org.llrp.ltk.types.UnsignedInteger;
import org.llrp.ltk.types.UnsignedShort;
import org.llrp.ltk.types.UnsignedShortArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devsource.rfid.leitor.Antena;
import br.com.devsource.rfid.leitor.Leitor;
import br.com.devsource.rfid.reader.AbstractRfid;
import br.com.devsource.rfid.tag.Tag;

public class LLRP extends AbstractRfid implements LLRPEndpoint {

  private static final int TIME_OUT = 6000;
  private final LLRPConnector connector;

  protected final UnsignedInteger ROSPEC_ID;

  protected static final Bit NO = new Bit(0);
  protected static final Bit YES = new Bit(1);

  private static final Logger LOGGER = LoggerFactory.getLogger(LLRP.class);

  protected LLRP(Leitor leitor) {
    super(leitor);
    if (leitor.porta() != 0) {
      connector = new LLRPConnector(this, leitor.hotName());
    } else {
      connector = new LLRPConnector(this, leitor.hotName(), leitor.porta());
    }
    ROSPEC_ID = new UnsignedInteger();
  }

  private void connect() throws LLRPConnectionAttemptFailedException {
    LOGGER.info("Iniciando conexão com leitor...");
    connector.connect();
    LOGGER.info("Conexão com leitor iniciada: " + leitor().toString());
  }

  private void enableROSpec() throws TimeoutException {
    ENABLE_ROSPEC rospec = new ENABLE_ROSPEC();
    rospec.setROSpecID(ROSPEC_ID);
    transact(rospec);
  }

  private void deleteROSpec() throws TimeoutException {
    DELETE_ROSPEC rospec = new DELETE_ROSPEC();
    rospec.setROSpecID(ROSPEC_ID);
    transact(rospec);
  }

  private UnsignedShortArray getIDAntennas() {
    UnsignedShortArray array = new UnsignedShortArray();
    leitor().antenasAtivas().forEach(antena -> array.add(new UnsignedShort(antena.numero())));
    return array;
  }

  private LLRPMessage transact(LLRPMessage message) throws TimeoutException {
    return connector.transact(message, TIME_OUT);
  }

  private ROReportSpec getReportSpec() {
    ROReportSpec reportSpec = new ROReportSpec();
    reportSpec.setROReportTrigger(new ROReportTriggerType(ROReportTriggerType.Upon_N_Tags_Or_End_Of_ROSpec));
    reportSpec.setN(new UnsignedShort(1));
    reportSpec.setTagReportContentSelector(getTagReportSelector());
    return reportSpec;
  }

  private void addROSpec() throws TimeoutException {
    ROSpec roSpec = new ROSpec();
    roSpec.setROSpecID(ROSPEC_ID);
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
    selector.setEnableAccessSpecID(YES);
    selector.setEnableAntennaID(YES);
    selector.setEnableChannelIndex(NO);
    selector.setEnableFirstSeenTimestamp(NO);
    selector.setEnableInventoryParameterSpecID(YES);
    selector.setEnableLastSeenTimestamp(NO);
    selector.setEnablePeakRSSI(NO);
    selector.setEnableROSpecID(NO);
    selector.setEnableSpecIndex(NO);
    selector.setEnableTagSeenCount(NO);
    List<AirProtocolEPCMemorySelector> airProtocolList = selector.getAirProtocolEPCMemorySelectorList();
    C1G2EPCMemorySelector memorySelector = new C1G2EPCMemorySelector();
    memorySelector.setEnableCRC(NO);
    memorySelector.setEnablePCBits(NO);
    airProtocolList.add(memorySelector);
    selector.setAirProtocolEPCMemorySelectorList(airProtocolList);
    return selector;
  }

  private AISpec getAiSpec() {
    AISpec aiSpec = new AISpec();
    AISpecStopTrigger aiSpecStopTrigger = new AISpecStopTrigger();
    aiSpecStopTrigger.setAISpecStopTriggerType(new AISpecStopTriggerType(AISpecStopTriggerType.Null));
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
    START_ROSPEC message = new START_ROSPEC();
    message.setROSpecID(ROSPEC_ID);
    transact(message);
  }

  private void stopSpec() throws TimeoutException {
    STOP_ROSPEC message = new STOP_ROSPEC();
    message.setROSpecID(ROSPEC_ID);
    transact(message);
  }

  private AntennaConfiguration configuracaoDaAntena(Antena antena) {
    AntennaConfiguration antennaConfig = new AntennaConfiguration();
    antennaConfig.setAntennaID(new UnsignedShort(antena.numero()));
    RFTransmitter transmitter = new RFTransmitter();
    transmitter.setTransmitPower(potenciaLLRP(antena.potencia()));
    transmitter.setHopTableID(new UnsignedShort(0));
    antennaConfig.setRFTransmitter(transmitter);
    return antennaConfig;
  }

  private UnsignedShort potenciaLLRP(int potencia) {
    return new UnsignedShort((255 * potencia) / 100);
  }

  private void configurarReader() throws TimeoutException {
    SET_READER_CONFIG config = new SET_READER_CONFIG();
    config.setResetToFactoryDefault(YES);
    List<AntennaConfiguration> antennasConfig = config.getAntennaConfigurationList();
    leitor().antenasAtivas().forEach(antena -> antennasConfig.add(configuracaoDaAntena(antena)));
    transact(config);
  }

  @Override
  public void startReader() {
    try {
      connect();
      deleteROSpec();
      configurarReader();
      addROSpec();
      enableROSpec();
      startSpec();
    } catch (TimeoutException | LLRPConnectionAttemptFailedException ex) {
      ex.printStackTrace();
      LOGGER.error(ex.getMessage());
      throw new RuntimeException("Erro de comunicação com leitor");
    }
  }

  @Override
  public void stopReader() {
    try {
      stopSpec();
      deleteROSpec();
    } catch (TimeoutException ex) {
      LOGGER.error(ex.getMessage());
    }
  }

  @Override
  public void errorOccured(String errorMessage) {
    LOGGER.error(errorMessage);
  }

  @Override
  public void messageReceived(LLRPMessage message) {
    if (message.getTypeNum() == RO_ACCESS_REPORT.TYPENUM) {
      RO_ACCESS_REPORT report = (RO_ACCESS_REPORT) message;
      List<TagReportData> reportDatas = report.getTagReportDataList();
      for (TagReportData tagReportData : reportDatas) {
        EPC_96 epc96 = (EPC_96) tagReportData.getEPCParameter();
        String EPC = epc96.getEPC().toString();
        int antena = 1;
        if (tagReportData.getAntennaID() != null) {
          antena = tagReportData.getAntennaID().getAntennaID().toInteger();
        }
        onRead(new Tag(EPC), antena);
      }
    }
  }

}
