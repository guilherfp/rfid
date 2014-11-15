package br.com.devsource.rfid.epc;

public interface EpcEncoder {

  String encodeEpc(String codeBar);

  String encodeEpc(String codeBar, int companyPrefixSize);

}
