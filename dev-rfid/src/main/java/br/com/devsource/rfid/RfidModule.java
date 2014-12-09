package br.com.devsource.rfid;

import br.com.devsource.rfid.leitor.Protocolo;

public interface RfidModule {

  String hostName();

  Protocolo getProtocolo();
}
