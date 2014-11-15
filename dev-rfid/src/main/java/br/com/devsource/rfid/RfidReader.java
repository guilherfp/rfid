package br.com.devsource.rfid;

public interface RfidReader extends RfidModule {

  void start();

  void stop();

  void addReaderEvent(RfidEvent event);

  void removeReaderEvent(RfidEvent event);

}
