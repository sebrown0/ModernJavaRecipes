/**
 * 
 */
package generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Brown
 *
 */
public class GenericNumberHelper <T extends Number> {

  public static void printList(List<?> list) {
    list.forEach(System.out::println);
  }
  
  public static <T extends Number> List<? super Number> addToList(List<? super Number> list, T number){
    List<? super Number> modifiedList = list;
    modifiedList.add(number);
    return modifiedList;
  }
  
  public static  List<? super Number> convertListOfNumbers(List<? extends Number> list){
    List<Number> nums = new ArrayList<Number>();
    
    for (Object number : list) {
      nums.add((Number) number);
    }
    return nums;
  }
}
