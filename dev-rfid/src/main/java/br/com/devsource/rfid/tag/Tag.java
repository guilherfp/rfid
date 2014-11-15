package br.com.devsource.rfid.tag;

import org.apache.commons.lang3.Validate;

public final class Tag {

  private String epc;

  Tag() {}

  public Tag(String epc) {
    Validate.notBlank(epc);
    this.epc = epc;
  }

  public String getEpc() {
    return epc;
  }

}
