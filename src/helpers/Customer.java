package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Customer POJO.
 * 
 * @author Steve Brown
 *
 */
public class Customer {

  public enum CUST_TYPE  {
    UNKNOWN(0), GOOD(1), BAD(2), UNWANTED(3);
    
    private static final Map<Integer, CUST_TYPE> custTypeMap = new HashMap<>();
    private int value;
    
    CUST_TYPE(int value) {
      this.value = value;
    }
    
    public static CUST_TYPE getCustType(int i) {
      return custTypeMap.getOrDefault(i, UNKNOWN);
    }
    
    static {
      for (CUST_TYPE type: values()) {
        custTypeMap.put(type.value, type);
      }
    }
  };
  
  protected CUST_TYPE thisCustomerType;
	protected String name;
	protected List<Order> orders = new ArrayList<>();
	
	public Customer(String name) {
		super();
		this.name = name;
		this.thisCustomerType = CUST_TYPE.UNKNOWN;
	}
	
	 public Customer(String name, CUST_TYPE type) {
	    super();
	    this.name = name;
	    this.thisCustomerType = type;
	  }

	public Customer addOrder(Order o) {
		this.orders.add(o);
		return this;
	}
	
	 public Customer addOrders(List<Order> orders) {
	    this.orders.addAll(orders);
	    return this;
	  }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
  public CUST_TYPE getThisCustomerType() {
    return thisCustomerType;
  }
  
  public Customer setThisCustomerType(CUST_TYPE thisCustomerType) {
    this.thisCustomerType = thisCustomerType;
    return this;
  }
  
  public Customer setThisCustomerType(int thisCustomerType) {
    this.thisCustomerType = CUST_TYPE.getCustType(thisCustomerType);
    return this;
  }

  @Override
  public String toString() {
    return String.format("Customer [thisCustomerType=%s, name=%s]", thisCustomerType, name);
  }
  
  
}
