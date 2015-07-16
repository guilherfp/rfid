package br.com.devsource.rfid.tag;

import java.io.Serializable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Guilherme Pacheco
 */
public final class Tag implements Comparable<Tag>, Serializable {
  private static final long serialVersionUID = 4941722287359138768L;

  private String epc;
  private String userMemory;

  Tag() {
    super();
  }

  public Tag(String epc, String userMemory) {
    this(epc);
    this.userMemory = userMemory;
  }

  public Tag(String epc) {
    Validate.notBlank(epc);
    this.epc = epc;
  }

  public String getEpc() {
    return ObjectUtils.defaultIfNull(epc, "").toUpperCase();
  }

  public String getUserMemory() {
    return ObjectUtils.defaultIfNull(userMemory, "").toUpperCase();
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
    StringBuilder builder = new StringBuilder(String.format("Tag: %S", epc));
    if (StringUtils.isNotBlank(userMemory)) {
      builder.append(String.format(", memory: %S", userMemory));
    }
    return builder.toString();
  }

}
