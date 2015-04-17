package br.com.devsource.rfid.reader.llrp;

import java.util.HashSet;
import java.util.Set;

import br.com.devsource.rfid.leitor.AntennaConfig;
import br.com.devsource.rfid.leitor.ReaderConfig;
import br.com.devsource.rfid.leitor.Protocol;

class LLRPCreator {

  public static ReaderConfig simpleLeitor(final String hostname, final int porta, final int... antenas) {
    return new ReaderConfig() {

      @Override
      public int compareTo(ReaderConfig o) {
        return 0;
      }

      @Override
      public Protocol getProtocol() {
        return Protocol.LLRP;
      }

      @Override
      public int getPort() {
        return porta;
      }

      @Override
      public String getHostName() {
        return hostname;
      }

      @Override
      public Set<AntennaConfig> getAntennas() {
        final Set<AntennaConfig> setAntenas = new HashSet<>();
        for (final int nrAntena : antenas) {
          setAntenas.add(new AntennaConfig() {

            @Override
            public int compareTo(AntennaConfig o) {
              return 0;
            }

            @Override
            public int getTransmitPower() {
              return 100;
            }

            @Override
            public int getNumber() {
              return nrAntena;
            }

            @Override
            public boolean isActive() {
              return true;
            }
          });
        }
        return setAntenas;
      }
    };
  }

}
