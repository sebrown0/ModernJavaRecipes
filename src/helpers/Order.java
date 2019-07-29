package helpers;

import java.util.function.Supplier;

/**
 * Trivial class to represent an order.
 * 
 * @author Steve Brown
 *
 */
public class Order implements Supplier<Order>{

	Long id;
	Long value;

	public Order() {
	  id = -1L;
	  value = 0L;
	}
	
	public Order(Long id) {
	  this.id = id;
	  this.value = id * 100;
	}
	
	public Order(Long id, Long value) {
		this.id = id;
		this.value = value;
	}

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

  @Override
  public String toString() {
    return String.format("Order [id=%s, value=%s]", id, value);
  }

  @Override
  public Order get() {
    return new Order();
  }

}
