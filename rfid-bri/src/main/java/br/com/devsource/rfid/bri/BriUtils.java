package br.com.devsource.rfid.bri;

import java.util.Set;
import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;

import com.intermec.datacollection.rfid.BRIReader;

import br.com.devsource.rfid.api.Antenna;
import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.ReadMode;
import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.core.ReaderUtils;

/**
 * @author Guilherme Pacheco
 */
final class BriUtils {

  private static final int MAX_POWER = 30;

  private BriUtils() {
    super();
  }

  public static String tpc(String hostname) {
    return StringUtils.prependIfMissing(hostname, "tcp://");
  }

  public static String schema(ReadCommand command) {
    StringJoiner joiner = new StringJoiner(",");
    command.getFields().forEach(c -> joiner.add(c.name()));
    return joiner.toString();
  }

  public static int operation(ReadCommand command) {
    if (ReadMode.ALL.equals(command.getReadMode())) {
      return BRIReader.TagReportOptions.EVENTALL;
    } else {
      return BRIReader.TagReportOptions.EVENT;
    }
  }

  public static int[] ants(ReaderConf conf) {
    Set<Antenna> antennas = conf.getActivesAntennas();
    int[] antenas = new int[antennas.size()];
    int index = 0;
    for (Antenna antena : antennas) {
      antenas[index] = antena.getNumber();
      index++;
    }
    return antenas;
  }

  public static int[] strength(ReaderConf conf) {
    Set<Antenna> antennas = conf.getActivesAntennas();
    int[] potencias = new int[antennas.size()];
    int index = 0;
    for (Antenna antena : antennas) {
      potencias[index] = ReaderUtils.scale(antena.getTransmitPower(), MAX_POWER);
      index++;
    }
    return potencias;
  }

}
