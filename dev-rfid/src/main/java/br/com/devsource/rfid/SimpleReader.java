package br.com.devsource.rfid;

import java.util.Set;

/**
 * @author Guilherme Pacheco
 */
public class SimpleReader implements ReaderConfig {

  private String hostname;
  private int port;
  private Protocol protocol;
  private Set<AntennaConfig> antennas;

  public SimpleReader(String hostname, int port, Protocol protocol, Set<AntennaConfig> antennas) {
    this.hostname = hostname;
    this.protocol = protocol;
    this.antennas = antennas;
    this.port = port;
  }

  @Override
  public String getHostName() {
    return hostname;
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public Protocol getProtocol() {
    return protocol;
  }

  @Override
  public Set<AntennaConfig> getAntennas() {
    return antennas;
  }

  @Override
  public int compareTo(ReaderConfig o) {
    return hostname.compareTo(o.getHostName());
  }

}
