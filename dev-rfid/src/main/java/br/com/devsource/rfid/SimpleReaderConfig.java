package br.com.devsource.rfid;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Guilherme Pacheco
 */
public class SimpleReaderConfig implements ReaderConfig {

  private String hostname;
  private int port;
  private Protocol protocol;
  private Set<AntennaConfig> antennas;

  public SimpleReaderConfig(String hostname, int port, Protocol protocol) {
    this(hostname, port, protocol, new HashSet<>());
  }

  public SimpleReaderConfig(String hostname, int port, Protocol protocol,
                            Set<AntennaConfig> antennas)
  {
    this.hostname = hostname;
    this.protocol = protocol;
    this.antennas = antennas;
    this.port = port;
  }

  @Override
  public String getHostname() {
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

  public void addAntenna(AntennaConfig antenna) {
    antennas.add(antenna);
  }

  @Override
  public int compareTo(ReaderConfig o) {
    return hostname.compareTo(o.getHostname());
  }

}
