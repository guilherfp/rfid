package br.com.devsource.rfid;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Guilherme Pacheco
 */
public interface ReaderConfig extends Comparable<ReaderConfig> {

  String getHostname();

  int getPort();

  Protocol getProtocol();

  default Mode getMode() {
    return Mode.UNIQUE;
  }

  Set<AntennaConfig> getAntennas();

  default int getTimeOut() {
    return 5000;
  }

  default Set<AntennaConfig> getActivesAntennas() {
    return getAntennas().stream().filter(AntennaConfig::isActive).collect(Collectors.toSet());
  }
}
