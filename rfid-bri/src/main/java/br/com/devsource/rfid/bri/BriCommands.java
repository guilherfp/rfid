package br.com.devsource.rfid.bri;

import com.intermec.datacollection.rfid.BRIParserException;
import com.intermec.datacollection.rfid.BasicReaderException;

import br.com.devsource.rfid.api.ReadCommand;
import br.com.devsource.rfid.api.RfidConnectionException;
import br.com.devsource.rfid.api.RfidException;
import br.com.devsource.rfid.api.gpio.GpioStatus;

/**
 * @author guilherme.pacheco
 */
final class BriCommands {

  private final BriReaderBuilder builder;

  public BriCommands(BriReaderBuilder builder) {
    this.builder = builder;
  }

  public void deleteAllTrigger() {
    builder.execute("TRIGGER DELETEALL");
  }

  public void gpoWrite(int number, GpioStatus status) {
    builder.execute(String.format("WRITEGPO %d %S", number, status.name()));
  }

  public void startReading(ReadCommand command) {
    try {
      String schema = BriUtils.schema(command);
      int operation = BriUtils.operation(command);
      builder.get().startReadingTags(null, schema, operation);
    } catch (BRIParserException ex) {
      throw new RfidException(builder.getConf(), "Invalid reading schema");
    } catch (BasicReaderException ex) {
      throw new RfidConnectionException(builder.getConf(), ex);
    }
  }

  public void stopReadingTags() {
    try {
      builder.get().stopReadingTags();
    } catch (BasicReaderException ex) {
      throw new RfidConnectionException(builder.getConf(), ex);
    }
  }

}
