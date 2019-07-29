/**
 * 
 */
package chapter9;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import helpers.Order;

/**
 * @author Steve Brown
 *
 */
public class OrderRetreiver {
  
  private static Map<Long, Order> cacheOfOrders = new HashMap<>();
  private static OrderDAO orderDAO;

  private OrderRetreiver() { }  

  /**
   * Attempts to get an order synchronously from for a given ID from a local source.
   * If the order is not cached in the local source attempt to ge it
   * from a 'remote' source using the Order DAO. Then cache the Order. 
   * 
   * @param id : The order ID.
   * @return The order as a future.
   * @throws NoSuchElementException
   */
  public static CompletableFuture<Order> getOrderSynch(long id) throws NoSuchElementException{
    try {
      Order order = getLocalOrder(id).orElseThrow();
      return CompletableFuture.completedFuture(order);  
    } catch (NoSuchElementException e) {
      Order order = getRemoteOrder(id).orElseThrow();
      CompletableFuture<Order> future = new CompletableFuture<Order>();
      cacheOfOrders.put(id, order);
      future.complete(order);
      return future;
    }
  }

  /**
   * Attempts to get an order asynchronously from for a given ID from a local source.
   * If the order is not cached in the local source attempt to ge it
   * from a 'remote' source using the Order DAO. Then cache the Order. 
   * 
   * @param id : The order ID.
   * @return The order as a future.
   * @throws NoSuchElementException
   */
  public static CompletableFuture<Order> getOrderAsynch(long id) throws NoSuchElementException{
    try {
      Order order = getLocalOrder(id).orElseThrow();
      return CompletableFuture.completedFuture(order);  
    } catch (NoSuchElementException e) {      
      return CompletableFuture.supplyAsync(() -> {
        Order order = getRemoteOrder(id).orElseThrow();
        cacheOfOrders.put(id, order);
        return order;
      });
    }
  }
  
  /**
   * Get an order from for a given ID from the local cache.
   * 
   * @param id : The order ID.
   * @return An Optional order.
   */
  public static Optional<Order> getLocalOrder(long id) {
    return Optional.ofNullable(cacheOfOrders.get(id));
  }

  /**
   * Get an order from for a given ID from a remote source.
   * An artificial delay is added to simulate getting the Order 
   * from a remote source.
   * 
   * @param id : The order ID.
   * @return An Optional order.
   * @throws NoSuchElementException
   */
  public static Optional<Order> getRemoteOrder(long id) throws NoSuchElementException{
    try {
      // Simulate getting from a remote device.
      Thread.sleep(100);
      // Simulate a retrieval error.
      if(id == -1) { throw new NoSuchElementException(); }
    } catch (InterruptedException | NoSuchElementException IGNORED) { }
    
    return orderDAO.getOrder(id);
  }
     
  /*
   * Setters below.
   */
  public static void setCacheOfOrders(Map<Long, Order> cacheOfOrders) {
    OrderRetreiver.cacheOfOrders = cacheOfOrders;
  }

  public static void setOrderDAO(OrderDAO orderDAO) {
    OrderRetreiver.orderDAO = orderDAO;
  }
    
}
