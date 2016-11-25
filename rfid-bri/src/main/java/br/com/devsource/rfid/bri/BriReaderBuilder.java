package br.com.devsource.rfid.bri;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;

import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.RfidConnectionException;

/**
 * @author Guilherme Pacheco
 */
class BriReaderBuilder {

  private final ReaderConf conf;
  private Optional<BRIReader> briReader = Optional.empty();
  private List<Consumer<BRIReader>> consumers = new ArrayList<>();

  private static final String TCP = "tcp://";

  public BriReaderBuilder(ReaderConf conf) {
    Validate.notNull(conf);
    this.conf = conf;
  }

  public ReaderConf getConf() {
    return conf;
  }

  public BRIReader get() {
    if (!briReader.isPresent()) {
      BRIReader reader = create();
      consumers.forEach(c -> c.accept(reader));
      briReader = Optional.of(reader);
    }
    return briReader.get();
  }

  public void ifPresent(Consumer<BRIReader> consumer) {
    briReader.ifPresent(consumer);
  }

  public void config(Consumer<BRIReader> consumer) {
    if (briReader.isPresent()) {
      consumer.accept(briReader.get());
    } else {
      consumers.add(consumer);
    }
  }

  private BRIReader create() {
    try {
      String uri = StringUtils.prependIfMissing(conf.getHostname(), TCP);
      if (conf.getPort() != 0) {
        return new BRIReader(uri, conf.getPort());
      } else {
        return new BRIReader(uri);
      }
    } catch (BasicReaderException ex) {
      throw new RfidConnectionException(conf, ex);
    }
  }
}
