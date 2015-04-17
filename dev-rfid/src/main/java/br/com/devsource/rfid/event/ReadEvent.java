package br.com.devsource.rfid.event;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.leitor.ReaderConfig;
import br.com.devsource.rfid.tag.Tag;

public class ReadEvent {

  private final Tag tag;
  private final int antena;
  private final ReaderConfig leitor;

  public ReadEvent(Tag tag, ReaderConfig leitor, int antena) {
    Validate.notNull(tag);
    Validate.notNull(leitor);
    this.tag = tag;
    this.leitor = leitor;
    this.antena = antena;
  }

  public ReadEvent(Tag tag, ReaderConfig leitor) {
    this(tag, leitor, 1);
  }

  public ReaderConfig getLeitor() {
    return leitor;
  }

  public int getAntena() {
    return antena;
  }

  public Tag getTag() {
    return tag;
  }

  @Override
  public String toString() {
    return String.format("Leitor: %s, antena: %s, %s", leitor.hotName(), antena, tag.toString());
  }

}
