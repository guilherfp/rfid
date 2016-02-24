package br.com.devsource.rfid.api.tag;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Guilherme Pacheco
 */
public class TagTest {

  @Test(expected = NullPointerException.class)
  public void testTag_EpcNull() throws Exception {
    new Tag((String) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTag_EpcEmpty() throws Exception {
    new Tag("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTag_EpcBlank() throws Exception {
    new Tag(" ");
  }

  @Test
  public void testGetEpc() throws Exception {
    assertEquals("232A765ED687CAE876", new Tag("232a765ed687cae876").getEpc());
  }

  @Test
  public void testToString() throws Exception {
    Tag tag = new Tag("232a765ed687cae876");
    assertEquals("Tag: 232A765ED687CAE876", tag.toString());
  }

  @Test
  public void testToString_WithUserMemory() throws Exception {
    Tag tag = new Tag("232a765ed687cae876", "abcd12344Ad");
    assertEquals("Tag: 232A765ED687CAE876, memory: ABCD12344AD", tag.toString());
  }

}
