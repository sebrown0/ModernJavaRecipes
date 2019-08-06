package generics.test;

import static generics.GenericNumberHelper.addToList;
import static generics.GenericNumberHelper.convertListOfNumbers;
import static global_data.GlobalTestData.getListOfEmployees;
import static global_data.GlobalTestData.sampleNumbers;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import helpers.Employee;

class GenericsTests {
  
  @Test
  void addToGenericListOfNUmbers() {
    List<? super Number> addDble = addToList(convertListOfNumbers(sampleNumbers), 11.00);
    List<? super Number> addLong = addToList(addDble, 12L);
    Map<String, ?> numberMap = addLong.stream()
        .collect(Collectors.groupingBy(GenericsTests::objectName, Collectors.toList()));
    
    String result = numberMap.entrySet()
        .stream()
        .map(k -> k.getKey())
        .reduce("", (s1,s2) -> s1.concat(s2))
        .toString();

    assertEquals("IntegerLongDouble", result);
  }
  
  private static String objectName(Object object) {
    String name = object.getClass().getName(); 
    return name.substring(10, name.length());
  }
  
  @Test
  void genericComparable() {
    Map<Integer, Employee> emps = getListOfEmployees()
        .stream()
        .collect(Collectors.toMap(Employee::getId, Function.identity()));
    
    Entry<Integer, Employee> sansa = emps.entrySet().stream()
      .sorted(Map.Entry.comparingByValue())
      .findFirst().orElse(null);
    
    assertEquals("Sansa", sansa.getValue().getName());
  }

}
