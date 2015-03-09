package br.com.devsource.rfid.api.tag;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

public final class Tag implements Comparable<Tag>, Serializable {
  private static final long serialVersionUID = 4941722287359138768L;

  private String epc;
  private String userMemory = "";

  Tag() {}

  public Tag(String epc, String userMemory) {
    Validate.notBlank(epc);
    this.epc = epc.toUpperCase();
    if (userMemory != null) {
      this.userMemory = userMemory;
    }
  }

  public Tag(String epc) {
    this(epc, "");
  }

  public String getEpc() {
    return epc;
  }

  public String getUserMemory() {
    return userMemory;
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
  public int compareTo(Tag o) {
    return epc.compareTo(o.epc);
  }

  @Override
  public String toString() {
    return String.format("Tag = epc: %s", epc);
  }

}
