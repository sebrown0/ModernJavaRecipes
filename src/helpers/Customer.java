package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer POJO.
 * 
 * @author Steve Brown
 *
 */
public class Customer {

	protected String name;
	protected List<Order> orders = new ArrayList<>();
	
	public Customer(String name) {
		super();
		this.name = name;
	}

	public Customer addOrder(Order o) {
		this.orders.add(o);
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
}
