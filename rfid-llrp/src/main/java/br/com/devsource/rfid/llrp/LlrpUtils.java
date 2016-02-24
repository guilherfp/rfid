package br.com.devsource.rfid.llrp;

import org.llrp.ltk.generated.enumerations.AirProtocols;
import org.llrp.ltk.generated.parameters.C1G2EPCMemorySelector;
import org.llrp.ltk.generated.parameters.InventoryParameterSpec;
import org.llrp.ltk.generated.parameters.TagReportContentSelector;
import org.llrp.ltk.types.Bit;
import org.llrp.ltk.types.UnsignedShort;
import org.llrp.ltk.types.UnsignedShortArray;

import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.tag.ReadTagField;

/**
 * @author Guilherme Pacheco
 */
class LlrpUtils {

  public static final Bit NO = new Bit(0);
  public static final Bit YES = new Bit(1);

  private LlrpUtils() {
    super();
  }

  public static Bit of(boolean value) {
    return value ? YES : NO;
  }

  public static TagReportContentSelector tagReportSelector(ReadCommand command) {
    TagReportContentSelector selector = new TagReportContentSelector();
    selector.setAirProtocolEPCMemorySelectorList(selector.getAirProtocolEPCMemorySelectorList());
    selector.setEnableAntennaID(of(command.contains(ReadTagField.ANTENNA)));
    selector.setEnableAccessSpecID(YES);
    selector.setEnableChannelIndex(NO);
    selector.setEnableFirstSeenTimestamp(NO);
    selector.setEnableInventoryParameterSpecID(YES);
    selector.setEnableLastSeenTimestamp(NO);
    selector.setEnablePeakRSSI(NO);
    selector.setEnableROSpecID(NO);
    selector.setEnableSpecIndex(NO);
    selector.setEnableTagSeenCount(NO);
    selector.getAirProtocolEPCMemorySelectorList().add(memorySelector());
    return selector;
  }

  private static C1G2EPCMemorySelector memorySelector() {
    C1G2EPCMemorySelector memorySelector = new C1G2EPCMemorySelector();
    memorySelector.setEnablePCBits(NO);
    memorySelector.setEnableCRC(NO);
    return memorySelector;
  }

  public static UnsignedShortArray idAntennas(ReaderConf conf) {
    UnsignedShortArray array = new UnsignedShortArray();
    conf.getActivesAntennas().forEach(a -> array.add(new UnsignedShort(a.getNumber())));
    return array;
  }

  public static InventoryParameterSpec inventoryParameter() {
    InventoryParameterSpec spec = new InventoryParameterSpec();
    spec.setProtocolID(new AirProtocols(AirProtocols.EPCGlobalClass1Gen2));
    spec.setInventoryParameterSpecID(new UnsignedShort(1));
    return spec;
  }
}
