package br.com.devsource.rfid.leitor;

import java.util.Set;
import java.util.stream.Collectors;

public interface ReaderConfig extends Comparable<ReaderConfig> {

  String hotName();

  int porta();

  Protocolo protocolo();

  Set<AntennaConfig> antenas();

  default Set<AntennaConfig> antenasAtivas() {
    return antenas().stream().filter(AntennaConfig::ativa).collect(Collectors.toSet());
  }

}
