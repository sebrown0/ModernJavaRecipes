/**
 * 
 */
package helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import helpers.Customer.CUST_TYPE;

/**
 * POJO for a modern shop.
 * 
 * The modern shop is more advanced as it
 * holds records for the customer and their type.
 * 
 * @author Steve Brown
 *
 */
public class ModernShop implements ShopCustomers{

  private String shopName;
  private Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers = new HashMap<>();
  
  public ModernShop(String shopName) {
    this.shopName = shopName;
  }
    
  public ModernShop(String shopName, Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers) {
    this.shopName = shopName;
    this.mapOfCustomerTypeAndCustomers = mapOfCustomerTypeAndCustomers;
  } 

  public String getShopName() {
    return shopName;
  }
  
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  
  
  public Map<CUST_TYPE, List<Customer>> getMapOfCustomerTypeAndCustomers() {
    return mapOfCustomerTypeAndCustomers;
  }

  public void setMapOfCustomerTypeAndCustomers(Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers) {
    this.mapOfCustomerTypeAndCustomers = mapOfCustomerTypeAndCustomers;
  }
  
  @Override
  public String toString() {
    return String.format("Shop [shopName=%s]", shopName);
  }

  @SuppressWarnings("unchecked")        // ONLY DOING THIS FOR EXPERIMENTAL PURPOSES.
  @Override
  public <R extends Collection<Customer>> R getCustomers() {
    Set<Customer> customers = new HashSet<Customer>();
    
    customers = mapOfCustomerTypeAndCustomers.entrySet()
        .stream().flatMap(e -> e.getValue().stream())
        .collect(Collectors.toSet());
    
    return (R) customers;
  }
  
}
