package br.com.devsource.rfid.bri;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.intermec.datacollection.rfid.BRIReader;

import br.com.devsource.rfid.api.ReadCommand.ReadCommandBuilder;
import br.com.devsource.rfid.api.ReadMode;
import br.com.devsource.rfid.api.tag.ReadTagField;

/**
 * @author Guilherme Pacheco
 */
public class BriUtilsTest {

  private ReadCommandBuilder builder;

  @Before
  public void setUp() throws Exception {
    builder = ReadCommandBuilder.create();
  }

  @Test
  public void testSchema() throws Exception {
    builder.add(ReadTagField.EPCID).add(ReadTagField.TAGID);
    assertEquals("EPCID,TAGID", BriUtils.schema(builder.build()));
  }

  @Test
  public void testOperation() throws Exception {
    builder.readMode(ReadMode.ALL);
    assertEquals(BRIReader.TagReportOptions.EVENTALL, BriUtils.operation(builder.build()));

    builder.readMode(ReadMode.UNIQUE);
    assertEquals(BRIReader.TagReportOptions.EVENT, BriUtils.operation(builder.build()));
  }

}
