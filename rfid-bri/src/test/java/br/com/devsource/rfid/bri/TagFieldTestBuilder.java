package br.com.devsource.rfid.bri;

import com.intermec.datacollection.rfid.TagField;

/**
 * @author guilherme.pacheco
 */
public class TagFieldTestBuilder {

  public static TagField[] fields(String... values) {
    TagField[] fields = new TagField[values.length];
    for (int i = 0; i < values.length; i++) {
      fields[i] = new TagField(values[i].getBytes());
    }
    return fields;
  }

}
