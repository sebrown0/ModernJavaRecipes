package utils.ListUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Convert a generic list with an ID field into a map. 
 * 
 * @author Steve Brown
 *
 * @param <T> type of the key.
 * @param <R> the object to store as the value.
 */
public class GenericListToMap <T, R extends IdAble<T>> {

  public Map<T, R> getMapFromList(List<R> list) {
    return list.stream().collect(Collectors.toMap(l -> { return l.getId(); }, t -> t));
  }

}