package helpers;

import utils.ListUtils.GenericId;
import utils.ListUtils.IdAble;

/**
 * Same as chapter3/helpers/Customer.java except this 
 * object uses a Generic Id so that it can be mapped from 
 * a list to a map using GenericListToMap.
 * 
 * @author Steve Brown
 *
 */
public class CustomerGenericId <T> extends Customer implements IdAble<T>{

  private GenericId<T> id;
  
  public CustomerGenericId(GenericId<T> id, String name) {
    super(name);
    this.id = id;
  }

  @Override
  public T getId() {
    return id.getId();
  }
}
