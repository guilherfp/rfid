package br.com.devsource.rfid.epc;

import br.com.devsource.rfid.tag.Tag;

public interface EpcDecoder {

  String decodeCodeBar(Tag tag);

}
