package br.com.devsource.rfid.leitor;

import java.util.Set;
import java.util.stream.Collectors;

public interface ReaderConfig extends Comparable<ReaderConfig> {

  String getHostName();

  int getPort();

  Protocol getProtocol();

  Set<AntennaConfig> getAntennas();

  default Set<AntennaConfig> getActiviesAntennas() {
    return getAntennas().stream().filter(AntennaConfig::isActive).collect(Collectors.toSet());
  }

}
