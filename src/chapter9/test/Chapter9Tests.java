package chapter9.test;

import static global_data.GlobalTestData.sampleNumbers;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import chapter9.FunWithFutures;
import chapter9.OrderDatabase;
import chapter9.OrderRetreiver;
import chapter9.BaseballScores.BoxscoreRetriever;
import chapter9.BaseballScores.GamePageLinkSupplier;
import chapter9.BaseballScores.Parser;
import chapter9.BaseballScores.Result;
import global_data.GlobalTestData;
import helpers.Order;
import utils.NumberManipulator;
import utils.SimpleFunctions;

class Chapter9Tests {

  static {
    OrderRetreiver.setCacheOfOrders(GlobalTestData.localOrders);
    OrderRetreiver.setOrderDAO(new OrderDatabase(GlobalTestData.remoteOrders));
  }
  
  @Test
  void isParallel() {
    assertFalse(Stream.generate(Math::random).limit(100).isParallel());
  }
  
  @Test
  void doInParallel() {
    sampleNumbers.parallelStream()
      .map(n -> { return String.format("Num[%d] Thread[%s]", n, Thread.currentThread().getName()); })
      .collect(Collectors.toList());
  }

  @Test
  void localOrder() {
    Optional<Order> o = OrderRetreiver.getLocalOrder(2L);
    assertEquals(2L, o.orElseGet(Order::new).getId());
  }
  
  @Test
  void remoteOrderGoodId() {
    Optional<Order> o = OrderRetreiver.getRemoteOrder(12L);
    assertEquals(12L, o.orElseGet(Order::new).getId());
  }
  
  @Test
  void remoteOrderBadId() {
    try {
      OrderRetreiver.getRemoteOrder(-1L);  
    } catch (NoSuchElementException e) {
      assertEquals("No value present", e.getMessage());
    }
  }
  
  @Test
  void genericOrderBadId() {
    try {
      OrderRetreiver.getOrderSynch(111L);  
    } catch (NoSuchElementException e) {
      assertEquals("No value present", e.getMessage());
    }
  }
  
  @Test
  void genericOrderSynchGoodId() {
    OrderRetreiver.getOrderSynch(12L);
    Optional<Order> o = OrderRetreiver.getLocalOrder(12L);
    assertEquals(12L, o.orElseGet(Order::new).getId());    
  }
  
  @Test
  void genericOrderAsynchGoodId() throws InterruptedException, ExecutionException {
    CompletableFuture<Order> order = OrderRetreiver.getOrderAsynch(14L);
    order.get();
    assertEquals(14L, OrderRetreiver.getLocalOrder(14L).get().getId());    
  }
  
  @Test
  void composeFutures() throws InterruptedException, ExecutionException {
    double byHalf = 0.5;
    CompletableFuture<Double> future = 
        CompletableFuture.supplyAsync(() -> OrderRetreiver.getLocalOrder(2).get().getValue())
          .thenCompose(n -> CompletableFuture.supplyAsync(() -> NumberManipulator.xplyBy(n, byHalf)));
        assertEquals(100.0, future.get());
  }
  
  @Test
  void combineFutures() throws InterruptedException, ExecutionException {
    CompletableFuture<Object> future = 
        CompletableFuture.supplyAsync(() -> OrderRetreiver.getLocalOrder(2).get().getValue())
          .thenCombine(CompletableFuture.supplyAsync(() -> OrderRetreiver.getLocalOrder(3).get().getValue()), SimpleFunctions.xplyNums);
        assertEquals(60_000L, future.get());
  }
  
  @Test
  void handleWithException() throws InterruptedException, ExecutionException {
    CompletableFuture<Integer> future = FunWithFutures.getIntegerCompletableFuture("abc");
    assertEquals(0, future.get());
  }
  
  @Test
  void handleWithoutException() throws InterruptedException, ExecutionException {
    CompletableFuture<Integer> future = FunWithFutures.getIntegerCompletableFuture("99");
    assertTrue(future.get() == 99);
  }

  /*
   * Baseball scores
   */
  @Test
  void gamePageLinks() {
    GamePageLinkSupplier linkSupplier = new GamePageLinkSupplier(null, 0);
    List<String> links = linkSupplier.getGamePageLinks(LocalDate.of(2017, Month.JUNE, 14));
    assertTrue(links.contains("day_14/gid_2017_06_14_texmlb_houmlb_1/"));
  }
  
  @Test
  void boxscoreRetreiverAndFileName() throws InterruptedException, ExecutionException {
    CompletableFuture<List<Result>> future =        
        CompletableFuture.supplyAsync(new GamePageLinkSupplier(LocalDate.of(2017, Month.JUNE, 14), 1))
          .thenApply(new BoxscoreRetriever()); 
    
    Result result = future.get().get(0);
    Parser parser = new Parser(System.getProperty("user.dir") + "\\TestOutput");
    parser.saveResultToFile(result);
    assertTrue(parser.createFileName(result).contains("June_14_2017_Arizona_Diamondbacks_at_Detroit_Tigers.txt"));
  }
  
  @Test
  void combineBoxscoreFutures() throws InterruptedException, ExecutionException {
    Parser parser = new Parser(System.getProperty("user.dir") + "\\TestOutput");
    
    CompletableFuture<List<Result>> future =        
        CompletableFuture.supplyAsync(new GamePageLinkSupplier(LocalDate.of(2017, Month.MAY, 5), 3))
          .thenApply(new BoxscoreRetriever());
    
    CompletableFuture<Void> futureWrite = future.thenAcceptAsync(parser::saveResultList)
        .exceptionally(e -> {
          System.err.println(e.getMessage());
          return null;
        }); 
    
    CompletableFuture<OptionalInt> futureMaxScore = future.thenApplyAsync(parser::maxScore);
    CompletableFuture<Optional<Result>> futureMaxGame = future.thenApplyAsync(parser::maxGame);    
    CompletableFuture<String> futureMaxCombined = combineMaxResults(futureMaxScore, futureMaxGame);

    CompletableFuture.allOf(futureMaxCombined, futureWrite).join();
    
    assertEquals("Highest score: 23 - Max Game: May 7, 2017: Boston Red Sox 17, Minnesota Twins 6", futureMaxCombined.join());
  }
  
  private CompletableFuture<String> combineMaxResults(CompletableFuture<OptionalInt> futureMaxScore, 
                                                        CompletableFuture<Optional<Result>> futureMaxGame) {
    return futureMaxScore
        .thenCombineAsync(futureMaxGame,               
            (score, result) ->  String.format("Highest score: %d - Max Game: %s", score.orElse(0), result.orElse(null)));
  }
}
