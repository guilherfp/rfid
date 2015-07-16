package br.com.devsource.rfid.llrp;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.com.devsource.rfid.AntennaConfig;
import br.com.devsource.rfid.Protocol;
import br.com.devsource.rfid.ReaderConfig;
import br.com.devsource.rfid.SimpleAntennaConfig;
import br.com.devsource.rfid.SimpleReaderConfig;

/**
 * @author Guilherme Pacheco
 */
public class LLRPReaderTest {

  @Test
  public void testToString() throws Exception {
    Set<AntennaConfig> antennas = new HashSet<>();
    antennas.add(new SimpleAntennaConfig(1, 100));
    antennas.add(new SimpleAntennaConfig(2, 100));
    ReaderConfig config = new SimpleReaderConfig("192.168.1.1", 5084, Protocol.LLRP, antennas);
    LLRPReader reader = new LLRPReader(config);
    assertEquals("Reader LLRP: 192.168.1.1:5084 [(1, 29dB), (2, 29dB)]", reader.toString());
  }

  @Test(expected = NullPointerException.class)
  public void testLLRPReader_ConfiguracaoInvalida() throws Exception {
    new LLRPReader(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLLRPReader_SemAntena() throws Exception {
    ReaderConfig config = new SimpleReaderConfig("192.168.1.1", 5084, Protocol.LLRP);
    new LLRPReader(config);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLLRPReader_SemAntenaAtiva() throws Exception {
    SimpleReaderConfig config = new SimpleReaderConfig("192.168.1.1", 5084, Protocol.LLRP);
    config.addAntenna(new SimpleAntennaConfig(1, 100, false));
    new LLRPReader(config);
  }
}
