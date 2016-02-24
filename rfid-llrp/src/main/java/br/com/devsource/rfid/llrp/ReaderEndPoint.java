package br.com.devsource.rfid.llrp;

import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.parameters.EPC_96;
import org.llrp.ltk.generated.parameters.TagReportData;
import org.llrp.ltk.net.LLRPEndpoint;
import org.llrp.ltk.types.LLRPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public class ReaderEndPoint implements LLRPEndpoint {

  private final ReaderLlrp readerLlrp;

  private static final Logger LOGGER = LoggerFactory.getLogger(ReaderEndPoint.class);

  public ReaderEndPoint(ReaderLlrp readerLlrp) {
    this.readerLlrp = readerLlrp;
  }

  @Override
  public void errorOccured(String message) {
    LOGGER.error(message);
  }

  @Override
  public void messageReceived(LLRPMessage message) {
    if (message.getTypeNum() == RO_ACCESS_REPORT.TYPENUM) {
      execute(message);
    }
  }

  private void execute(LLRPMessage message) {
    RO_ACCESS_REPORT report = (RO_ACCESS_REPORT) message;
    for (TagReportData data : report.getTagReportDataList()) {
      EPC_96 epc96 = (EPC_96) data.getEPCParameter();
      ReadEvent readEvent = readEvent(data, epc96);
      readerLlrp.onRead(readEvent);
    }
  }

  private ReadEvent readEvent(TagReportData data, EPC_96 epc96) {
    return new ReadEvent(tag(epc96), readerLlrp.getConf(), antenna(data));
  }

  private static int antenna(TagReportData data) {
    return data.getAntennaID() != null ? data.getAntennaID().getAntennaID().toInteger() : 0;
  }

  private static Tag tag(EPC_96 epc96) {
    return new Tag(epc96.getEPC().toString());
  }
}
