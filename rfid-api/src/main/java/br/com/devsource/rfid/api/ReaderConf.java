package br.com.devsource.rfid.api;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Guilherme Pacheco
 */
public interface ReaderConf {

  String getHostname();

  int getPort();

  default ReadMode getMode() {
    return ReadMode.UNIQUE;
  }

  Set<Antenna> getAntennas();

  default int getTimeOut() {
    return 5000;
  }

  default Set<Antenna> getActivesAntennas() {
    return getAntennas().stream().filter(Antenna::isActive).collect(Collectors.toSet());
  }
}
