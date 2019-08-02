/**
 * 
 */
package chapter10;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Trivial example of default/static and private methods in an interface.
 * 
 * @author Steve Brown
 *
 */
public interface SumNums {

  static int addOdds(int... nums) {
    return add(i -> i % 2 != 0, nums);
  }
  
  static int addEvens(int... nums) {
    return add(i -> i % 2 == 0, nums);
  }
  
  private static int add(IntPredicate p, int... nums) {
    return IntStream.of(nums).filter(p).sum();
  }

}
