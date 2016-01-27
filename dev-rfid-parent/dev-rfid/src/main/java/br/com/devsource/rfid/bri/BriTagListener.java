package br.com.devsource.rfid.bri;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import com.intermec.datacollection.rfid.TagEvent;
import com.intermec.datacollection.rfid.TagEventListener;
import com.intermec.datacollection.rfid.TagField;

import br.com.devsource.rfid.RfidField;
import br.com.devsource.rfid.tag.Tag;
import br.com.devsource.rfid.tag.TagBuilder;

/**
 * @author Guilherme Pacheco
 */
public class BriTagListener implements TagEventListener {

  private final BriReader briReader;
  private final List<RfidField> rfidFields;

  public BriTagListener(BriReader briReader, RfidField[] fields) {
    rfidFields = Arrays.asList(fields);
    this.briReader = briReader;
  }

  @Override
  public void tagRead(TagEvent event) {
    briReader.onReadTag(createTag(event), antena(event));
  }

  private int antena(TagEvent event) {
    MutableInt antena = new MutableInt(0);
    extract(event, RfidField.ANTENNA).ifPresent(f -> antena.setValue(f.getDataInt()));
    return antena.getValue();
  }

  private Tag createTag(TagEvent event) {
    TagBuilder tagBuilder = TagBuilder.create();
    tagId(tagBuilder, event);
    epc(tagBuilder, event);
    return tagBuilder.build();
  }

  private void epc(TagBuilder tagBuilder, TagEvent event) {
    String epc = new String(event.getTag().getTagKey());
    tagBuilder.epc(fromHex(epc));
  }

  private void tagId(TagBuilder tagBuilder, TagEvent event) {
    extract(event, RfidField.TAGID).ifPresent(f -> tagBuilder.tagId(fromHex(f.getDataString())));
  }

  private Optional<TagField> extract(TagEvent event, RfidField rfidField) {
    if (rfidFields.contains(rfidField)) {
      int index = rfidFields.indexOf(rfidField);
      return Optional.ofNullable(event.getTag().tagFields.getField(index));
    } else {
      return Optional.empty();
    }
  }

  private String fromHex(String hexValue) {
    return StringUtils.removeStartIgnoreCase(hexValue, "H");
  }

}
