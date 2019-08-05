/**
 * 
 */
package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO for a corner shop.
 * 
 * The corner shop is slightly more basic than a modern shop as it
 * doesn't hold records for the type of customer.
 * 
 * @author Steve Brown
 *
 */
public class CornerShop implements ShopCustomers{

  private String shopName;
  private List<Customer> customers = new ArrayList<>();
  
  public CornerShop(String shopName) {
    this.shopName = shopName;
  }
  
  public CornerShop(String shopName, List<Customer> customers) {
    this.shopName = shopName;
    this.customers = customers;
  }
  
  public String getShopName() {
    return shopName;
  }
  
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  
  @SuppressWarnings("unchecked")          // ONLY DOING THIS FOR EXPERIMENTAL PURPOSES.
  @Override
  public List<Customer> getCustomers() {
    return customers;
  }
  
  public List<Customer> getCustomers(CornerShop shop) {
    return this.customers;
  }

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }
  
  @Override
  public String toString() {
    return String.format("Shop [shopName=%s, customers=%s]", shopName, customers);
  }
  
}
