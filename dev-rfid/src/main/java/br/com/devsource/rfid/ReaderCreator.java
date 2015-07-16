package br.com.devsource.rfid;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Guilherme Pacheco
 */
public class ReaderCreator {

  private ReaderCreator() {
    super();
  }

  public static ReaderConfig simpleReader(String hostname, int port, Protocol protocol,
      int... antennas) {
    return new SimpleReaderConfig(hostname, port, protocol, buidAntennas(antennas));
  }

  private static Set<AntennaConfig> buidAntennas(int... antenas) {
    Set<AntennaConfig> setAntenas = new HashSet<>();
    for (int nrAntena : antenas) {
      setAntenas.add(new SimpleAntennaConfig(nrAntena, 100, true));
    }
    return setAntenas;
  }

}
