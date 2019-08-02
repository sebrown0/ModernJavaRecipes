/**
 * 
 */
package chapter9;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import helpers.Customer;
import helpers.Order;
import helpers.Shop;

/**
 * Static methods for Chapter10Tests to help keep the code 'clean'.
 * @author Steve Brown
 *
 */
public class HelperMethods {

  /**
   * Given a customer return a stream of the value their orders.
   * @param c customer
   * @return a stream of the order values.
   */
  public static LongStream getOrderValue(Customer c) {
    return c.getOrders().stream().mapToLong(Order::getValue); 
  }
  
  /**
   * Get Stream of customers for a shop.
   * @param s Shop
   * @return Stream of customers.
   */
  public static Stream<Customer> getStreamOfCustomers(Shop s){
    return s.getCustomers().stream();
  }
}
