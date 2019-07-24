package helpers;

import java.util.function.Supplier;

import utils.ListUtils.GenericId;
import utils.ListUtils.IdAble;

/**
 * Book POJO with a generic id.
 * 
 * @author Steve Brown
 *
 * @param <T> the type of the id.
 */
public class Book<T> implements IdAble<T>, Supplier<Book<T>>{
  
  private GenericId<T> id;
  private String name;
  private double price;
  
  public Book() {
    this.id = new GenericId<T>();
    this.name = "Default Book Name";
    this.price = 00.00;
  }
  
  public Book(GenericId<T> id, String name, double price) {
    super();
    this.id = id;
    this.name = name;
    this.price = price;
  }
  
  public T getId() {
    return id.getId();
  }

  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public double getPrice() {
    return price;
  }
  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    long temp;
    temp = Double.doubleToLongBits(price);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
  
  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Book)) { 
      return false; 
    }else if(o != null) { 
      Book<?> other = (Book<?>)o;
      if(other.id != null) {
        return (this.id == other.id) ? true : false;
      }
    }
    return false;
  }

  @Override
  public Book<T> get() {
    return new Book<T>();
  }

}
