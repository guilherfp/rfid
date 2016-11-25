package br.com.devsource.rfid.all;

import java.util.HashSet;
import java.util.Set;

import br.com.devsource.rfid.api.Antenna;
import br.com.devsource.rfid.api.ReaderConf;

/**
 * @author Guilherme Pacheco
 */
public class SimpleReaderConf implements ReaderConf {

  private int port;
  private String hostname;
  private Set<Antenna> antennas;

  public SimpleReaderConf() {
    antennas = new HashSet<>();
  }

  @Override
  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  @Override
  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public Set<Antenna> getAntennas() {
    return antennas;
  }

  public void setAntennas(Set<Antenna> antennas) {
    this.antennas = antennas;
  }
}
