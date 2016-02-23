package br.com.devsource.rfid.api.tag;

public class TagBuilder {

  String epc;
  String tagId;
  String userMemory;

  private TagBuilder() {
    super();
  }

  public static TagBuilder create() {
    return new TagBuilder();
  }

  public TagBuilder epc(String epc) {
    this.epc = epc;
    return this;
  }

  public TagBuilder tagId(String tagId) {
    this.tagId = tagId;
    return this;
  }

  public TagBuilder userMemory(String userMemory) {
    this.userMemory = userMemory;
    return this;
  }

  public Tag build() {
    return new Tag(this);
  }

}
