package br.com.devsource.rfid.bri;

import com.intermec.datacollection.rfid.TagEvent;
import com.intermec.datacollection.rfid.TagEventListener;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.event.ReadEvent;

/**
 * @author Guilherme Pacheco
 */
final class TagListener implements TagEventListener {

  private final ReaderBri readerBri;
  private final TagBriExtractor tagExtractor;

  public TagListener(ReaderBri readerBri, ReadCommand command) {
    tagExtractor = new TagBriExtractor(command);
    this.readerBri = readerBri;
  }

  @Override
  public void tagRead(TagEvent event) {
    ReadEvent readEvent = tagExtractor.readEvent(event, readerBri.getConf());
    readerBri.onRead(readEvent);
  }

}
