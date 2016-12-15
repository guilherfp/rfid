package br.com.devsource.rfid.bri;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.Validate;

import com.intermec.datacollection.rfid.BRIReader;
import com.intermec.datacollection.rfid.BasicReaderException;

import br.com.devsource.rfid.api.ReaderConf;
import br.com.devsource.rfid.api.RfidConnectionException;

/**
 * @author Guilherme Pacheco
 */
final class BriReaderBuilder {

  private final ReaderConf conf;
  private final List<Consumer<BRIReader>> consumers;
  private final BriCommands commands;

  private BRIReader briReader;

  public BriReaderBuilder(ReaderConf conf) {
    commands = new BriCommands(this);
    consumers = new ArrayList<>();
    Validate.notNull(conf);
    this.conf = conf;
  }

  public ReaderConf getConf() {
    return conf;
  }

  public BRIReader get() {
    if (briReader == null) {
      briReader = connect();
    }
    return briReader;
  }

  private BRIReader connect() {
    BRIReader reader = create();
    consumers.forEach(c -> c.accept(reader));
    return reader;
  }

  public void execute(String command) {
    try {
      get().execute(command);
    } catch (BasicReaderException ex) {
      throw new IllegalArgumentException(String.format("Comando BRI: %s, inválido", command));
    }
  }

  public BriCommands commands() {
    return commands;
  }

  public void config(Consumer<BRIReader> consumer) {
    if (briReader != null) {
      consumer.accept(briReader);
    } else {
      consumers.add(consumer);
    }
  }

  public void ifPresent(Consumer<BRIReader> consumer) {
    if (briReader != null) {
      consumer.accept(briReader);
    }
  }

  private BRIReader create() {
    try {
      String uri = BriUtils.tpc(conf.getHostname());
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
