package br.com.devsource.rfid.all.factory;

import java.util.HashMap;
import java.util.Map;

import br.com.devsource.rfid.all.Protocol;

/**
 * @author guilherme.pacheco
 */
final class ReaderFactories {

  private Map<Protocol, ReaderBuilder> builders;

  public ReaderFactories() {
    builders = new HashMap<>(2);
    builders.put(Protocol.LLRP, new ReaderLlrpBuilder());
    builders.put(Protocol.BRI, new ReaderBriBuilder());
  }

  public ReaderBuilder get(Protocol protocol) {
    return builders.get(protocol);
  }

}
