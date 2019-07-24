package chapter4.test;

import static chapter4.Sorting.sortByLengthMaxWithSorted;
import static chapter4.Sorting.sortByLengthMinWithComparator;
import static chapter4.Sorting.sortByLengthThenAlpha;
import static chapter4.Sorting.sortByScoreThenLastThenFirst;
import static chapter4.Sorting.sortNatural;
import static global_data.GlobalTestData.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import helpers.Employee;
import helpers.Golfer;

class Chapter4Tests {

  /*
   * Sorting
   */
  @Test
  void sortedNatural() {
    assertEquals("a", sortNatural(sampleStrings).get(0));
  }

  @Test
  void sortedByLengthMaxWithSorted() {
    assertEquals("strings", sortByLengthMaxWithSorted(sampleStrings).get(0));
  }

  @Test
  void sortedByLengthMinWithComparator() {
    assertEquals("a", sortByLengthMinWithComparator(sampleStrings).get(0));
  }

  @Test
  void sortedByLengthMinWithComparatorAndNaturalOrder() {
    assertEquals("a", sortByLengthThenAlpha(sampleStrings).get(0));
  }

  @Test
  void sortByScoreThenLastAndFirstNames() {
    assertEquals("Jack", sortByScoreThenLastThenFirst(getListOfGolfers()).get(0).getFirst());
  }

  /*
   * Mapping.
   */
  @Test
  void listOfSuperHeroes() {
    assertEquals("Invisible Boy", getListOfSuperHeroes().get(4));
  }

  @Test
  void listOfVillans() {
    assertTrue(getListOfVillans().contains("The Furriers"));
  }

  @Test
  void mapGolfersScore() {
    Map<String, Integer> golfersAndTheirScores = getListOfGolfers().stream()
        .collect(Collectors.toMap(Golfer::getFirst, Golfer::getScore));
    assertEquals(70, golfersAndTheirScores.get("Tiger"));
  }

  /*
   * Mapping and sorting.
   */
  @Test
  void findTheLenOfEachSentence() {    
    Map<Integer, Long> lineLen = null;
    try (Stream<String> lines = Files.lines(Paths.get(SHAKESPEARE_FILE))) {
	    lineLen = lines.filter(s -> s.length() > 75)        
	      .collect(Collectors.groupingBy(String::length, Collectors.counting()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(1, lineLen.get(85));
  }
  
  @Test
  void findFirstLineWithMoreThan75Chars() {
    Optional<String> line = null;
    try (Stream<String> lines = Files.lines(Paths.get(SHAKESPEARE_FILE))) {
      line = lines.filter(s -> s.length() > 75).findFirst();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(line.get().contains("FRENCH LORDS"));
  }
  
  @Test
  void findHighestOrderValue() {
   OptionalLong maxOrder = getListOfCustomers().stream()
       .flatMap(c -> c.getOrders().stream()).mapToLong(o -> o.getValue()).max();
   assertEquals(900, maxOrder.getAsLong());
  }
  
  @Test
  void findHighestSalary() {
    OptionalInt maxSalary = getListOfEmployees().stream()
        .mapToInt(Employee::getSalary)
        .max();
    assertEquals(1_000_000, maxSalary.getAsInt());
  }
  
  @Test
  void findEmployeeWithHighestSalary() {
    Employee emp = getListOfEmployees().stream()
        .max(Comparator.comparingInt(Employee::getSalary)).orElse(null);
    assertEquals("Tywin", emp.getName());
  }
  
  @Test
  void findHighestPaidEmployeeForEachDept() {
    Map<String, Optional<Employee>> emps = getListOfEmployees().stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.maxBy(Comparator.comparingInt(Employee::getSalary))));
    assertEquals(125_000, emps.get("Stark").get().getSalary());    
  }
    
  public SortedSet<String> oddLengthStringSet(String... strings) {
    
    Collector<String, ?, SortedSet<String>> intoSet =    
        Collector.of(TreeSet<String>::new,     
            SortedSet::add,
              (left, right) -> { 
                left.addAll(right);     
                  return left;    
               },
              Collections::unmodifiableSortedSet);         
    
    return Stream.of(strings).filter(s -> s.length() > 0).collect(intoSet);    
  }
}
