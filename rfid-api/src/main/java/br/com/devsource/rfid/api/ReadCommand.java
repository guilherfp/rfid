package br.com.devsource.rfid.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.devsource.rfid.api.tag.ReadTagField;

/**
 * @author Guilherme Pacheco
 */
public class ReadCommand {

  private List<ReadTagField> fields;
  private ReadMode readMode;

  public ReadCommand() {
    fields = new ArrayList<>();
    readMode = ReadMode.UNIQUE;
  }

  public ReadCommand(ReadCommandBuilder builder) {
    fields = builder.fields;
    readMode = builder.readMode;
  }

  public List<ReadTagField> getFields() {
    return new ArrayList<>(fields);
  }

  public boolean contains(ReadTagField field) {
    return fields.contains(field);
  }

  public ReadMode getReadMode() {
    return readMode;
  }

  @Override
  public String toString() {
    return String.format("ReadCommand [fields: %s, mode: %s]", fields, readMode);
  }

  public static class ReadCommandBuilder {

    private List<ReadTagField> fields;
    private ReadMode readMode;

    private ReadCommandBuilder() {
      fields = new ArrayList<>();
      readMode = ReadMode.UNIQUE;
    }

    public ReadCommandBuilder fields(List<ReadTagField> fields) {
      if (fields != null) {
        this.fields = fields;
      }
      return this;
    }

    public ReadCommandBuilder add(ReadTagField... fields) {
      this.fields.addAll(Arrays.asList(fields));
      return this;
    }

    public ReadCommandBuilder readMode(ReadMode readMode) {
      if (readMode != null) {
        this.readMode = readMode;
      }
      return this;
    }

    public static ReadCommandBuilder create() {
      return new ReadCommandBuilder();
    }

    public ReadCommand build() {
      return new ReadCommand(this);
    }
  }
}
