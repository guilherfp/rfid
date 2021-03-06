package br.com.devsource.rfid.api.tag;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Guilherme Pacheco
 */
public final class Tag implements Comparable<Tag> {

  private String epc;
  private String tagId;
  private String userMemory;

  Tag() {
    super();
  }

  public Tag(String epc) {
    Validate.notBlank(epc);
    this.epc = epc;
  }

  public Tag(String epc, String userMemory) {
    this(epc);
    this.userMemory = userMemory;
  }

  public Tag(TagBuilder builder) {
    this(builder.epc);
    userMemory = builder.userMemory;
    tagId = builder.tagId;
  }

  public String getEpc() {
    return epc.toUpperCase();
  }

  public String getUserMemory() {
    return ObjectUtils.defaultIfNull(userMemory, "").toUpperCase();
  }

  public String getTagId() {
    return ObjectUtils.defaultIfNull(tagId, "").toUpperCase();
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
    if (obj == null || obj.getClass() != Tag.class) {
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
