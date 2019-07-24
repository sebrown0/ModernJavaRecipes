package utils;

import java.util.List;

/**
 * Trivial number manipulation.
 * 
 * @author Steve Brown
 *
 */
public class NumberManipulator {
     
  public static int xplyBy(int num, int multiplier) {
    return num * multiplier;
  }

  public static <T extends Number> boolean divisibleBy(T num, T divisor) {
    return num.doubleValue() % divisor.doubleValue() == 0;
  }

  public static Integer sumListOfNums(List<Integer> list) {
    return list.stream().mapToInt(n -> n.intValue()).sum();
  }
  
}
