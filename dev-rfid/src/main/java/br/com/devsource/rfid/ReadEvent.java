package br.com.devsource.rfid;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public class ReadEvent {

  private final Tag tag;
  private final int antena;
  private final ReaderConfig config;

  public ReadEvent(Tag tag, ReaderConfig config, int antena) {
    Validate.notNull(tag);
    Validate.notNull(config);
    this.tag = tag;
    this.config = config;
    this.antena = antena;
  }

  public ReaderConfig getConfig() {
    return config;
  }

  public int getAntena() {
    return antena;
  }

  public Tag getTag() {
    return tag;
  }

  @Override
  public String toString() {
    return String
      .format("Leitor: %s, antena: %s, %s", config.getHostname(), antena, tag.toString());
  }

}
