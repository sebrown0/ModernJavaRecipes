/**
 * 
 */
package chapter10;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import helpers.CornerShop;
import helpers.Customer;
import helpers.Customer.CUST_TYPE;
import helpers.ModernShop;
import helpers.Order;
import helpers.ShopCustomers;

/**
 * Methods for Chapter10Tests to help keep the code 'clean'.
 * @author Steve Brown
 *
 */
public class Chapter10Helpers {

  /**
   * Given a customer return a stream of the value their orders.
   * @param c customer
   * @return a stream of the order values.
   */
  public static LongStream getOrderValue(Customer c) {
    return c.getOrders().stream().mapToLong(Order::getValue); 
  }

  /**
   * Get Stream of customers for a shop from the List of customers for that shop.
   * @param s Shop
   * @return Stream of customers.
   */
  public static Stream<Customer> getStreamOfCustomersFromList(CornerShop s){
    return s.getCustomers().stream();
  }
  
  /**
   * Get Stream of customers for a shop from the Map of customers for that shop.
   * @param s Shop
   * @return Stream of customers.
   */
  public static Stream<Customer> getStreamOfCustomersFromMap(ModernShop s){
    return s.getMapOfCustomerTypeAndCustomers().entrySet().stream()
        .flatMap(shop -> shop.getValue().stream());
  }
  
  /**
   * Get an optional customer for a given type.
   * 
   * @param type Customer type.
   * @param customer the customer.
   * @return an optional customer.
   */
  public static Optional<Customer> findCustomerByType(CUST_TYPE type, Customer customer) {
    return Optional.ofNullable(customerIsOfType(type, customer) ? customer : null);  
  }
  
  public static boolean customerIsOfType(CUST_TYPE type, Customer customer) {
    return customer.getThisCustomerType().equals(type);
  }
  
  /**
   * Get a generic collection of customers from a shop.
   * There's no good reason for doing it this way. 
   * It's just a way to experiment with generics and polymorphism.
   * 
   * @param <R> type of the collection to return.
   * @param shopCustomers shop object.
   * @return a collection of customers.
   */
  public  <R extends Collection<Customer>> R getCustomersForShop(ShopCustomers shopCustomers){ 
    return shopCustomers.getCustomers();
  }
}
