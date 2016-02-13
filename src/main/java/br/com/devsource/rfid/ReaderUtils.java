package br.com.devsource.rfid;

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
   * @param porcentage Porcentagem da potência desejada.
   * @param maxValue Potência máxima.
   * @return Potência desejada.
   */
  public static int scale(double porcentage, int maxValue) {
    if (porcentage <= 0) {
      return 0;
    } else if (porcentage >= CEM_PORCENTO) {
      return maxValue;
    } else {
      return (int) (maxValue * (porcentage / CEM_PORCENTO));
    }
  }

}
