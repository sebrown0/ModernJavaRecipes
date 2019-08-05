/**
 * 
 */
package helpers;

import java.util.Collection;

/**
 * @author Steve Brown
 *
 */
public interface ShopCustomers {
  <R extends Collection<Customer>> R getCustomers();
}
