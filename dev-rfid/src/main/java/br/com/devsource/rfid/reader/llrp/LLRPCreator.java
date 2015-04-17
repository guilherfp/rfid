package br.com.devsource.rfid.reader.llrp;

import java.util.HashSet;
import java.util.Set;

import br.com.devsource.rfid.leitor.AntennaConfig;
import br.com.devsource.rfid.leitor.ReaderConfig;
import br.com.devsource.rfid.leitor.Protocolo;

class LLRPCreator {

  public static ReaderConfig simpleLeitor(final String hostname, final int porta, final int... antenas) {
    return new ReaderConfig() {

      @Override
      public int compareTo(ReaderConfig o) {
        return 0;
      }

      @Override
      public Protocolo protocolo() {
        return Protocolo.LLRP;
      }

      @Override
      public int porta() {
        return porta;
      }

      @Override
      public String hotName() {
        return hostname;
      }

      @Override
      public Set<AntennaConfig> antenas() {
        final Set<AntennaConfig> setAntenas = new HashSet<>();
        for (final int nrAntena : antenas) {
          setAntenas.add(new AntennaConfig() {

            @Override
            public int compareTo(AntennaConfig o) {
              return 0;
            }

            @Override
            public int potencia() {
              return 100;
            }

            @Override
            public int numero() {
              return nrAntena;
            }

            @Override
            public boolean ativa() {
              return true;
            }
          });
        }
        return setAntenas;
      }
    };
  }

}
