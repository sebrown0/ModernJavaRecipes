package helpers;

public class Order {

	Long id;
	Long value;

	public Order(Long id, Long value) {
		super();
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
		return "Order [Id=" + this.id + "]";
	}
}
