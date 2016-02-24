package br.com.devsource.rfid.core;

/**
 * Classe utilitária para {@link Reader}
 * @author guilherme.pacheco
 */
public final class ReaderUtils {

  private static final int MAX = 100;

  private ReaderUtils() {
    super();
  }

  /**
   * Gets a percentage value based on a maximum value
   * @param value Percentage of desired power.
   * @param maxValue Maximum power.
   * @return desired power.
   */
  public static int scale(double value, int maxValue) {
    if (value <= 0) {
      return 0;
    } else if (value >= MAX) {
      return maxValue;
    } else {
      return (int) (maxValue * (value / MAX));
    }
  }

}
