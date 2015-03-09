package br.com.devsource.rfid.api.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TagTest {

  @Test
  public void testGetEpc() throws Exception {
    assertEquals("232A765ED687CAE876", new Tag("232a765ed687cae876").getEpc());
  }

}
