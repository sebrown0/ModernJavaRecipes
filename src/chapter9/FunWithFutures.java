package chapter9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

/**
 * This class is not meant to be implemented or copied.
 * It's used for practising futures 
 * and implementing some of the ideas from Ken Kousen's book.
 * 
 * @author Steve Brown
 *
 */
public class FunWithFutures {
  
  public static BiFunction<Long, String, String> waitAndReturnString = (l, s) -> {
    try {
      Thread.sleep(l);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return s; 
  }; 

  public static <V> void printFutureResultIfNotCancelled(Future<V> future) {
    try {
      if(!future.isCancelled()) {
          System.out.println("Future result -> " + future.get());
      }else {
        System.out.println("Future was cancelled" );
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
  
  public static CompletableFuture<Integer> getIntegerCompletableFuture(String num){
    return CompletableFuture.supplyAsync(() -> Integer.parseInt(num))
        .handle((value, exception) -> value != null ? value : 0);
  }
}
