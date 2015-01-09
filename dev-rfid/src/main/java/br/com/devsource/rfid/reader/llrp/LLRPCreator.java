package br.com.devsource.rfid.reader.llrp;

import java.util.HashSet;
import java.util.Set;

import br.com.devsource.rfid.api.leitor.Antena;
import br.com.devsource.rfid.api.leitor.Leitor;
import br.com.devsource.rfid.api.leitor.Protocolo;

public class LLRPCreator {

  public static Leitor simpleLeitor(final String hostname, final int porta, final int... antenas) {
    return new Leitor() {

      @Override
      public int compareTo(Leitor o) {
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
      public Set<Antena> antenas() {
        final Set<Antena> setAntenas = new HashSet<>();
        for (final int nrAntena : antenas) {
          setAntenas.add(new Antena() {

            @Override
            public int compareTo(Antena o) {
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
