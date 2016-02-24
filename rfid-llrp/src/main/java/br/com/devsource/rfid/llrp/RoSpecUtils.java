package br.com.devsource.rfid.llrp;

import org.llrp.ltk.generated.enumerations.AISpecStopTriggerType;
import org.llrp.ltk.generated.enumerations.ROReportTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecStartTriggerType;
import org.llrp.ltk.generated.enumerations.ROSpecState;
import org.llrp.ltk.generated.enumerations.ROSpecStopTriggerType;
import org.llrp.ltk.generated.parameters.AISpec;
import org.llrp.ltk.generated.parameters.AISpecStopTrigger;
import org.llrp.ltk.generated.parameters.ROBoundarySpec;
import org.llrp.ltk.generated.parameters.ROReportSpec;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.generated.parameters.ROSpecStartTrigger;
import org.llrp.ltk.generated.parameters.ROSpecStopTrigger;
import org.llrp.ltk.types.UnsignedByte;
import org.llrp.ltk.types.UnsignedInteger;
import org.llrp.ltk.types.UnsignedShort;

import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReaderConf;

/**
 * @author Guilherme Pacheco
 */
public class RoSpecUtils {

  private RoSpecUtils() {
    super();
  }

  public static ROSpec roSpec(ReaderLlrp readerLlrp, ReadCommand command) {
    ROSpec roSpec = new ROSpec();
    roSpec.setROSpecID(readerLlrp.getRoSpecId());
    roSpec.setPriority(new UnsignedByte(0));
    roSpec.setCurrentState(new ROSpecState(ROSpecState.Disabled));
    roSpec.setROBoundarySpec(boundarySpec());
    roSpec.addToSpecParameterList(aiSpec(readerLlrp.getConf()));
    roSpec.setROReportSpec(reportSpec(command));
    return roSpec;
  }

  private static ROReportSpec reportSpec(ReadCommand command) {
    ROReportSpec spec = new ROReportSpec();
    int type = ROReportTriggerType.Upon_N_Tags_Or_End_Of_ROSpec;
    spec.setROReportTrigger(new ROReportTriggerType(type));
    spec.setTagReportContentSelector(LlrpUtils.tagReportSelector(command));
    spec.setN(new UnsignedShort(1));
    return spec;
  }

  private static AISpec aiSpec(ReaderConf conf) {
    AISpec aiSpec = new AISpec();
    aiSpec.addToInventoryParameterSpecList(LlrpUtils.inventoryParameter());
    aiSpec.setAntennaIDs(LlrpUtils.idAntennas(conf));
    aiSpec.setAISpecStopTrigger(aiSpecStopTrigger());
    return aiSpec;
  }

  private static AISpecStopTrigger aiSpecStopTrigger() {
    AISpecStopTrigger trigger = new AISpecStopTrigger();
    trigger.setAISpecStopTriggerType(new AISpecStopTriggerType(AISpecStopTriggerType.Null));
    trigger.setDurationTrigger(new UnsignedInteger(0));
    return trigger;
  }

  private static ROBoundarySpec boundarySpec() {
    ROBoundarySpec boundarySpec = new ROBoundarySpec();
    boundarySpec.setROSpecStartTrigger(rosSpecStartTrigger());
    boundarySpec.setROSpecStopTrigger(roSpecStopTrigger());
    return boundarySpec;
  }

  private static ROSpecStartTrigger rosSpecStartTrigger() {
    ROSpecStartTrigger startTrigger = new ROSpecStartTrigger();
    startTrigger.setROSpecStartTriggerType(new ROSpecStartTriggerType(ROSpecStartTriggerType.Null));
    return startTrigger;
  }

  private static ROSpecStopTrigger roSpecStopTrigger() {
    ROSpecStopTrigger stopTrigger = new ROSpecStopTrigger();
    stopTrigger.setDurationTriggerValue(new UnsignedInteger(0));
    stopTrigger.setROSpecStopTriggerType(new ROSpecStopTriggerType(ROSpecStopTriggerType.Null));
    return stopTrigger;
  }
}
