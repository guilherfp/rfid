package br.com.devsource.rfid.bri;

import static br.com.devsource.rfid.bri.TagFieldTestBuilder.fields;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.intermec.datacollection.rfid.Tag;
import com.intermec.datacollection.rfid.TagEvent;
import com.intermec.datacollection.rfid.TagFields;

import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReadCommand.ReadCommandBuilder;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.event.ReadEvent;
import br.com.devsource.rfid.api.tag.ReadTagField;

/**
 * @author guilherme.pacheco
 */
@RunWith(MockitoJUnitRunner.class)
public class TagBriExtractorTest {

  @Mock
  private TagEvent event;
  @Mock
  private ReaderConf conf;
  @Mock
  private Tag tag;

  private TagFields tagFields;
  private ReadCommandBuilder commandBuilder;

  @Before
  public void setUp() throws Exception {
    commandBuilder = ReadCommandBuilder.create();
    tagFields = new TagFields();
    tag.tagFields = tagFields;

    when(event.getTag()).thenReturn(tag);
  }

  @Test
  public void testReadEvent_WithAntenna() throws Exception {
    ReadCommand command = commandBuilder.add(ReadTagField.ANTENNA).build();
    TagBriExtractor tagExtractor = new TagBriExtractor(command);

    tagFields.setFields(fields("3"));
    when(tag.getTagKey()).thenReturn("H3039606283c5f840000989da".getBytes());

    ReadEvent readEvent = tagExtractor.readEvent(event, conf);

    assertThat(readEvent.getAntenna()).isEqualTo(3);
    assertThat(readEvent.getTag().getEpc()).isEqualTo("3039606283C5F840000989DA");
  }

  @Test
  public void testReadEvent_WithAntennaAndTagId() throws Exception {
    ReadCommand command = commandBuilder.add(ReadTagField.ANTENNA, ReadTagField.TAGID).build();
    TagBriExtractor tagExtractor = new TagBriExtractor(command);

    tagFields.setFields(fields("1", "E28011302000300F"));
    when(tag.getTagKey()).thenReturn("H3039606283c5f840000989da".getBytes());

    ReadEvent readEvent = tagExtractor.readEvent(event, conf);

    assertThat(readEvent.getAntenna()).isEqualTo(1);
    assertThat(readEvent.getTag().getEpc()).isEqualTo("3039606283C5F840000989DA");
    assertThat(readEvent.getTag().getTagId()).isEqualTo("E28011302000300F");
  }

}
