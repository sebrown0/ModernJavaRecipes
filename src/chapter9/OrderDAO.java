/**
 * 
 */
package chapter9;

import java.util.Optional;

import helpers.Order;

/**
 * @author Steve Brown
 *
 */
public interface OrderDAO {
  Optional<Order> getOrder(Long id);
}
