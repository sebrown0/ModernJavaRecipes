/**
 * 
 */
package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.Customer.CUST_TYPE;

/**
 * POJO for a basic shop.
 * 
 * @author Steve Brown
 *
 */
public class Shop {

  private String shopName;
  private List<Customer> customers = new ArrayList<>();
  private Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers = new HashMap<>();
  
  public Shop(String shopName) {
    this.shopName = shopName;
  }
  
  public Shop(String shopName, List<Customer> customers) {
    this.shopName = shopName;
    this.customers = customers;
  }
  
  public Shop(String shopName, Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers) {
    this.shopName = shopName;
    this.mapOfCustomerTypeAndCustomers = mapOfCustomerTypeAndCustomers;
  } 

  public String getShopName() {
    return shopName;
  }
  
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  
  public List<Customer> getCustomers() {
    return customers;
  }
  
  public List<Customer> getCustomers(Shop shop) {
    return this.customers;
  }
  
  public Map<CUST_TYPE, List<Customer>> getMapOfCustomerTypeAndCustomers() {
    return mapOfCustomerTypeAndCustomers;
  }

  public void setMapOfCustomerTypeAndCustomers(Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers) {
    this.mapOfCustomerTypeAndCustomers = mapOfCustomerTypeAndCustomers;
  }

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }
  
  @Override
  public String toString() {
    return String.format("Shop [shopName=%s, customers=%s]", shopName, customers);
  }
  
}
