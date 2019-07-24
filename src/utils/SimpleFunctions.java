package utils;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Simple functions, consumers etc for demonstrating composed functionality.
 * 
 * @author Steve Brown
 *
 */
public class SimpleFunctions {
  
  public static Function<Integer, Integer> squareNum = n -> n * n;
  public static Function<String, Integer> stringLen = s -> s.length();
  public static Function<String, String> addFullStop = s -> s + ".";
  
  public static Function<String, String> removeFullStop = 
    s -> {
      return (s.charAt(s.length() - 1) == '.') ? s.substring(0, s.length() - 1) : s;
    };
    
  public static BiFunction<String, String, String> concatStrings = (s1, s2) -> s1.concat(s2);
  public static BiFunction<String, String, String> concatStringsAndTerminate = concatStrings.andThen(addFullStop);
  
  public static Function<String, Integer> squareStringLen = stringLen.andThen(squareNum);
  public static Function<String, Integer> addToStringAndGetSquare = stringLen.compose(addFullStop).andThen(squareNum);
  
  public static Consumer<String> printer = System.out::println;
  public static Function<String, String> print = s -> {printer.accept(s); return s;}; 
  public static BiFunction<String, Logger, String> logData = (s, l) -> { l.info(s); return s;};
  public static BiConsumer<String, Logger> printThenLog = (s,l) -> { print.apply("Print: " + s); logData.apply("Log: " + s, l); };
  public static BiFunction<String, Logger, String> logAndPrint = logData.andThen(print);
  
  public static IntPredicate isPerfectSquare = n -> { return ((Math.sqrt(n) % 1) == 0); };
  public static IntPredicate isTriangularNumber = n -> { return ((Math.sqrt(8 * n + 1) - 1) / 2) % 1 == 0; };  
  public static IntPredicate numberisTriangularAndPerfect = isTriangularNumber.and(isPerfectSquare);
  public static Predicate<Integer> isNotZero = n -> n != 0;
  
  public static IntBinaryOperator sumInts = (a, b) -> a + b; 
  
  public static IntBinaryOperator div = (a, b) -> { 
    try {
      return a / b;
    } catch (Exception e) {
      System.out.println(String.format("Cannot divide by [%d]", b));
    }
    return 0;
  };
}
