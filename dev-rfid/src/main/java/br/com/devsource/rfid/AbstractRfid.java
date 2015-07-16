package br.com.devsource.rfid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

import org.apache.commons.lang3.Validate;

import br.com.devsource.rfid.tag.Tag;

/**
 * @author Guilherme Pacheco
 */
public abstract class AbstractRfid implements RfidModule {

  private RfidDevice device;
  private final ReaderConfig config;
  private List<RfidHandler> handlers = new ArrayList<>();
  private BooleanProperty connected = new SimpleBooleanProperty();

  protected AbstractRfid(ReaderConfig config) {
    Validate.notNull(config);
    this.config = config;
    Validate.notEmpty(config.getActivesAntennas());
    connected.addListener(checkConnection());
  }

  private ChangeListener<? super Boolean> checkConnection() {
    return (obs, oldValue, newValue) -> {
      if (SimpleRfidDevice.NAO_ENCONTRADO.equals(device) && newValue) {
        device = null;
      }
    };
  }

  @Override
  public final ReaderConfig getConfig() {
    return config;
  }

  @Override
  public final void addHandler(RfidHandler handler) {
    if (handler != null) {
      handlers.add(handler);
    }
  }

  @Override
  public final void removeHandler(RfidHandler handler) {
    if (handler != null) {
      handlers.remove(handler);
    }
  }

  protected final void onRead(Tag tag, int antena) {
    handlers.forEach(handler -> handler.call(new ReadEvent(tag, config, antena)));
  }

  protected final void setConnected(boolean connected) {
    this.connected.set(connected);
  }

  @Override
  public final ReadOnlyBooleanProperty isConnected() {
    return connected;
  }

  protected final Stream<AntennaConfig> getAntennas() {
    return getConfig().getActivesAntennas().stream();
  }

  @Override
  public final RfidDevice getDevice() {
    if ((device == null)) {
      device = requestDevice();
    }
    return device;
  }

  protected abstract RfidDevice requestDevice();

}
