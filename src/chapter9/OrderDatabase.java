/**
 * 
 */
package chapter9;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import helpers.Order;

/**
 * Simulates an Order Database
 * 
 * @author Steve Brown
 *
 */
public class OrderDatabase implements OrderDAO {

  private  Map<Long, Order> orderDB = new HashMap<Long, Order>();
  
  public OrderDatabase(Map<Long, Order> orderDB) {
    this.orderDB = orderDB;
  }

  @Override
  public Optional<Order> getOrder(Long id) {
    return Optional.ofNullable(orderDB.get(id));
  }

}
