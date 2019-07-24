package utils.ListUtils;

/**
 * Generic ID value. Can by used by an object as an identity.
 * 
 * @author Steve Brown
 *
 * @param <T> the type of the Id.
 */
public class GenericId <T> implements IdAble<T>{
  
  private T id;
  
  public GenericId() { }
  
  public GenericId(T id) {
    this.id = id;
  }
    
  @Override
  public T getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof GenericId<?>)) { 
      return false; 
    }else if(o != null) { 
      GenericId<?> other = (GenericId<?>) o;
      if(other.id != null) {
        return (this.id == other.id) ? true : false;
      }
    }
    return false;
  }    
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
}
