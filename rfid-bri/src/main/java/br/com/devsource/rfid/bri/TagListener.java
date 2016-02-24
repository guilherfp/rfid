package br.com.devsource.rfid.bri;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import com.intermec.datacollection.rfid.TagEvent;
import com.intermec.datacollection.rfid.TagEventListener;
import com.intermec.datacollection.rfid.TagField;

import br.com.devsource.rfid.api.tag.ReadTagField;
import br.com.devsource.rfid.api.tag.Tag;
import br.com.devsource.rfid.api.tag.TagBuilder;

/**
 * @author Guilherme Pacheco
 */
class TagListener implements TagEventListener {

  private final ReaderBri readerBri;
  private final List<ReadTagField> rfidFields;

  public TagListener(ReaderBri readerBri, ReadTagField[] fields) {
    rfidFields = Arrays.asList(fields);
    this.readerBri = readerBri;
  }

  @Override
  public void tagRead(TagEvent event) {
    Tag tag = createTag(event);
    int antena = antena(event);
    // readerBri.onReadTag(createTag(event), antena(event));
  }

  private int antena(TagEvent event) {
    MutableInt antena = new MutableInt(0);
    extract(event, ReadTagField.ANTENNA).ifPresent(f -> antena.setValue(f.getDataInt()));
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
    extract(event, ReadTagField.TAGID).ifPresent(f -> tagBuilder.tagId(fromHex(f.getDataString())));
  }

  private Optional<TagField> extract(TagEvent event, ReadTagField tagField) {
    if (rfidFields.contains(tagField)) {
      int index = rfidFields.indexOf(tagField);
      return Optional.ofNullable(event.getTag().tagFields.getField(index));
    } else {
      return Optional.empty();
    }
  }

  private String fromHex(String hexValue) {
    return StringUtils.removeStartIgnoreCase(hexValue, "H");
  }

}
