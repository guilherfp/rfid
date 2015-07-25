package br.com.devsource.rfid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Guilherme Pacheco
 */
public class ReaderUtilsTest {

  @Test
  public void testScale() throws Exception {
    assertEquals(15, ReaderUtils.scale(50, 30));
  }

  @Test
  public void testScale_ValorNegativo() throws Exception {
    assertEquals(0, ReaderUtils.scale(-1, 30));
  }

  @Test
  public void testScale_ValorMaximo() throws Exception {
    assertEquals(30, ReaderUtils.scale(100, 30));
  }

  @Test
  public void testScale_AcimaDoMaximo() throws Exception {
    assertEquals(30, ReaderUtils.scale(150, 30));
  }

}
