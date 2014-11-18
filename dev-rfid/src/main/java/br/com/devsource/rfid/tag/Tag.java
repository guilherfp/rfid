package br.com.devsource.rfid.tag;

import org.apache.commons.lang3.Validate;

public final class Tag {

  private String epc;

  Tag() {}

  public Tag(String epc) {
    Validate.notBlank(epc);
    this.epc = epc.toUpperCase();
  }

  public String getEpc() {
    return epc;
  }

  @Override
  public int hashCode() {
    return epc.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (obj.getClass() != Tag.class)) {
      return false;
    }
    Tag other = (Tag) obj;
    return epc.equals(other.epc);
  }

  @Override
  public String toString() {
    return String.format("Tag = epc: %s", epc);
  }

}
