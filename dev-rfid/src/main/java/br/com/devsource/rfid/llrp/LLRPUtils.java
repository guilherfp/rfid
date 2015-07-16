package br.com.devsource.rfid.llrp;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.parameters.EPC_96;
import org.llrp.ltk.generated.parameters.TagReportData;
import org.llrp.ltk.types.Bit;
import org.llrp.ltk.types.LLRPMessage;

import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
class LLRPUtils {

  public static final Bit NO = new Bit(0);
  public static final Bit YES = new Bit(1);

  private LLRPUtils() {
    super();
  }

  public static void extractedTags(LLRPMessage message, Consumer<Entry<Tag, Integer>> consumer) {
    RO_ACCESS_REPORT report = (RO_ACCESS_REPORT) message;
    for (TagReportData tagReportData : report.getTagReportDataList()) {
      EPC_96 epc96 = (EPC_96) tagReportData.getEPCParameter();
      int antena = antenna(tagReportData);
      consumer.accept(new AbstractMap.SimpleEntry<>(getTag(epc96), antena));
    }
  }

  private static int antenna(TagReportData tagReportData) {
    if (tagReportData.getAntennaID() != null) {
      return tagReportData.getAntennaID().getAntennaID().toInteger();
    } else {
      return 1;
    }
  }

  private static Tag getTag(EPC_96 epc96) {
    return new Tag(epc96.getEPC().toString());
  }
}
