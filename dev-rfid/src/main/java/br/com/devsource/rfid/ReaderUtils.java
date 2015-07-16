package br.com.devsource.rfid;

/**
 * @author Guilherme Pacheco
 */
public class ReaderUtils {

  private ReaderUtils() {
    super();
  }

  public static int scale(double porcentage, int max) {
    if (porcentage <= 0) {
      return 0;
    } else if (porcentage >= 100) {
      return max;
    } else {
      return (int) (max * (porcentage / 100));
    }
  }

}
