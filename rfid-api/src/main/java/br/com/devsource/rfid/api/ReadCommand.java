package br.com.devsource.rfid.api;

import java.util.ArrayList;
import java.util.List;

import br.com.devsource.rfid.api.tag.ReadTagField;

/**
 * @author Guilherme Pacheco
 */
public class ReadCommand {

  private List<ReadTagField> fields;
  private ReadMode mode;

  public ReadCommand() {
    fields = new ArrayList<>();
    mode = ReadMode.UNIQUE;
  }

  public List<ReadTagField> getFields() {
    return new ArrayList<>(fields);
  }

  public void addField(ReadTagField field) {
    fields.add(field);
  }

  public ReadMode getMode() {
    return mode;
  }

  @Override
  public String toString() {
    return String.format("ReadCommand [fields: %s, mode: %s]", fields, mode);
  }

}
