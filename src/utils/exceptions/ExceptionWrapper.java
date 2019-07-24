package utils.exceptions;

import java.util.function.Function;

/**
 * Generic wrapper for exceptions.
 * It takes a functional interface as an argument so
 * it can be used in a lambda expression.
 * 
 * @author Steve Brown
 *
 */
public class ExceptionWrapper {

  @FunctionalInterface
  public interface FunctionWithException<T, R, E extends Exception>{
    R apply(T t) throws E;
  }
  
  public static <T, R, E extends Exception> Function<T, R> wrap(FunctionWithException<T, R, Exception> fe) {
    return arg -> {
      try {
        return fe.apply(arg);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}
