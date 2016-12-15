package br.com.devsource.rfid.bri;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.intermec.datacollection.rfid.TagEvent;
import com.intermec.datacollection.rfid.TagField;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.tag.ReadTagField;
import br.com.devsource.rfid.api.tag.Tag;
import br.com.devsource.rfid.api.tag.TagBuilder;

/**
 * @author guilherme.pacheco
 */
final class TagBriExtractor {

  private static final String HEX_PREFIX = "H";
  private final ReadCommand command;

  public TagBriExtractor(ReadCommand command) {
    this.command = command;
  }

  public ReadEvent readEvent(TagEvent event, ReaderConf conf) {
    Tag tag = createTag(event);
    int antena = antena(event);
    return new ReadEvent(tag, conf, antena);
  }

  private int antena(TagEvent event) {
    return extract(event, ReadTagField.ANTENNA).map(TagField::getDataInt).orElse(0);
  }

  private Tag createTag(TagEvent event) {
    TagBuilder tagBuilder = TagBuilder.create();
    extractTagId(tagBuilder, event);
    extractEpc(tagBuilder, event);
    return tagBuilder.build();
  }

  private void extractEpc(TagBuilder tagBuilder, TagEvent event) {
    tagBuilder.epc(fromHex(new String(event.getTag().getTagKey())));
  }

  private void extractTagId(TagBuilder tagBuilder, TagEvent event) {
    extract(event, ReadTagField.TAGID).ifPresent(f -> tagBuilder.tagId(fromHex(f.getDataString())));
  }

  private Optional<TagField> extract(TagEvent event, ReadTagField tagField) {
    if (command.contains(tagField)) {
      int index = command.getFields().indexOf(tagField);
      return Optional.ofNullable(event.getTag().tagFields.getField(index));
    } else {
      return Optional.empty();
    }
  }

  private String fromHex(String hexValue) {
    return StringUtils.removeStartIgnoreCase(hexValue, HEX_PREFIX);
  }

}
