package br.com.devsource.rfid.core;

/**
 * Classe utilitária para {@link Reader}
 * @author guilherme.pacheco
 */
public final class ReaderUtils {

  private static final int CEM_PORCENTO = 100;

  private ReaderUtils() {
    super();
  }

  /**
   * Obtem uma valor percentual com base em um valor máximo
   * @param value Porcentagem da potência desejada.
   * @param maxValue Potência máxima.
   * @return Potência desejada.
   */
  public static int scale(double value, int maxValue) {
    if (value <= 0) {
      return 0;
    } else if (value >= CEM_PORCENTO) {
      return maxValue;
    } else {
      return (int) (maxValue * (value / CEM_PORCENTO));
    }
  }

}
