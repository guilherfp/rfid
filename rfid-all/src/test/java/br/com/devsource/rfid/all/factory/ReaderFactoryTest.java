package br.com.devsource.rfid.all.factory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import br.com.devsource.rfid.all.Protocol;
import br.com.devsource.rfid.all.factory.ReaderFactory;
import br.com.devsource.rfid.api.Reader;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.bri.ReaderBri;
import br.com.devsource.rfid.llrp.ReaderLlrp;

/**
 * @author guilherme.pacheco
 */
public class ReaderFactoryTest {

  private ReaderFactory readerFactory;
  private ReaderConf readerConf;

  @Before
  public void setUp() throws Exception {
    readerConf = mock(ReaderConf.class);

    readerFactory = new ReaderFactory();
  }

  @Test
  public void testReader_LLRP() throws Exception {
    Reader reader = readerFactory.reader(Protocol.LLRP).reader(readerConf);

    assertThat(reader, is(notNullValue()));
    assertThat(reader, is(instanceOf(ReaderLlrp.class)));
  }

  @Test
  public void testReader_BRI() throws Exception {
    Reader reader = readerFactory.reader(Protocol.BRI).reader(readerConf);

    assertThat(reader, is(notNullValue()));
    assertThat(reader, is(instanceOf(ReaderBri.class)));
  }

  @Test
  public void testReaderGpio_BRI() throws Exception {
    Reader reader = readerFactory.readerGpio(Protocol.BRI).readerGpio(readerConf);

    assertThat(reader, is(notNullValue()));
    assertThat(reader, is(instanceOf(ReaderBri.class)));
  }

}
