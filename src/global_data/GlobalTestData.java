package global_data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import helpers.Customer;
import helpers.Employee;
import helpers.Golfer;
import helpers.Order;

/**
 * Data used by tests from all packages.
 * 
 * @author Steve Brown
 *
 */
public class GlobalTestData {

  public static final String SHAKESPEARE_FILE = "C:/users/brown/eclipse-workspace/java/com/sebrown/ModernJavaRecipes/resource/shakespeare.txt";
  
  public static String[] sampleString = { "this", "is", "an", "array", "of", "strings" };
  
  public static String[] sampleStringWithNulls = { "this", null, "is", "an", null, "array", "of", "strings" };
  
  public static String[] sampleStringWithNullsReplaced = { "this", "TEXT", "is", "an", "TEXT", "array", "of", "strings" };

  public static List<String> sampleStrings = List.of("this", "is", "a", "list", "of", "strings");

  public static List<Integer> sampleNumbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
  
  public static String palindrome = "Madam, in Eden, I'm Adam";
  
  public static String passage = "So thou, thy self out-going in thy noon. Unlooked on diest unless thou get a son";

  public static List<Golfer> getListOfGolfers() {
    return Arrays.asList(new Golfer("Jack", "Nicklaus", 68), new Golfer("Tiger", "Woods", 70),
        new Golfer("Tom", "Watson", 70), new Golfer("Ty", "Webb", 68), new Golfer("Bubba", "Watson", 70));
  }

  public static List<String> getListOfSuperHeroes() {
    return Stream
        .of("Mr. Furious", "The Blue Raja", "The Shoveler", "The Bowler", "Invisible Boy", "The Spleen", "The Sphinx")
        .collect(Collectors.toList());
  }

  public static Set<String> getListOfVillans() {
    return Stream.of("Casanova Frankenstein", "The Disco Boys", "The Not-So-Goodie Mob", "The Suits", "The Suzies",
        "The Furriers", "The Furriers").collect(Collectors.toSet());
  }
  
  public static List<Customer> getListOfCustomers(){
    return List.of(
        new Customer("Bob").addOrder(new Order(1L, 100L)).addOrder(new Order(2L, 200L)).addOrder(new Order(3L, 300L)),
        new Customer("Tom").addOrder(new Order(4L, 400L)).addOrder(new Order(5L, 500L)).addOrder(new Order(6L, 600L)),
        new Customer("Sally").addOrder(new Order(7L, 700L)).addOrder(new Order(8L, 800L)).addOrder(new Order(9L, 900L)));
  }

  public static List<Employee> getListOfEmployees(){
    return List.of(    
        new Employee(1, "Cersei", 250_000, "Lannister"),
        new Employee(2, "Jamie", 150_000, "Lannister"),
        new Employee(3, "Tyrion", 1_000, "Lannister"), 
        new Employee(4, "Tywin", 1_000_000, "Lannister"),
        new Employee(5, "Jon Snow", 75_000, "Stark"),  
        new Employee(6, "Robb", 120_000, "Stark"),  
        new Employee(7, "Eddard", 125_000, "Stark"), 
        new Employee(8, "Sansa",  0, "Stark"),    
        new Employee(9, "Arya", 1_000, "Stark"));
  }
  
}
