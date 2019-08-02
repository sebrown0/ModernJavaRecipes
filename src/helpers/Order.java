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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Order other = (Order) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }
  
  

}
