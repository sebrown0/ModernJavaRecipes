package global_data;

import static java.util.stream.Collectors.groupingBy;
import static helpers.BlogPost.BlogPostType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import helpers.BlogPost;
import helpers.Customer;
import helpers.Customer.CUST_TYPE;
import helpers.Employee;
import helpers.Golfer;
import helpers.Order;
import helpers.Shop;

/**
 * Data used by tests from all packages.
 * 
 * @author Steve Brown
 *
 */
public class GlobalTestData {
  
  public static final String PROJECT_ROOT = System.getProperty("user.dir");
  
  public static final String SHAKESPEARE_FILE = PROJECT_ROOT + "\\resource\\shakespeare.txt";
  
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
      
  public static List<BlogPost> getBlogs(){
    return Arrays.asList(
        new BlogPost("Blog1", "A1", BlogPostType.GUIDE, 3),
        new BlogPost("Blog2", "A1", BlogPostType.NEWS, 111),
        new BlogPost("Blog3", "A2", BlogPostType.REVIEW, 213),
        new BlogPost("Blog4", "A3", BlogPostType.NEWS, 32),
        new BlogPost("Blog5", "A4", BlogPostType.GUIDE, 723)
        );
  }
  
  public static List<Customer> getListOfCustomers(){
    return List.of(
        new Customer("Bob").addOrder(new Order(1L, 100L)).addOrder(new Order(2L, 200L)).addOrder(new Order(3L, 300L)),
        new Customer("Tom").addOrder(new Order(4L, 400L)).addOrder(new Order(5L, 500L)).addOrder(new Order(6L, 600L)),
        new Customer("Sally").addOrder(new Order(7L, 700L)).addOrder(new Order(8L, 800L)).addOrder(new Order(9L, 900L)));
  }

  public static Map<CUST_TYPE, List<Customer>> mapOfRandomCustomerTypeAndCustomers() {
    Random random = new Random();
    return getListOfCustomers()
        .stream()
        .map(c -> c.setThisCustomerType(random.nextInt(CUST_TYPE.values().length - 1)))
        .collect(groupingBy(Customer::getThisCustomerType));
  }
  
  public static Map<CUST_TYPE, List<Customer>> mapOfCustomerTypeAndCustomers() {
    Map<CUST_TYPE, List<Customer>> customersWithType = new HashMap<>();
    customersWithType.put(CUST_TYPE.BAD, Arrays.asList(getListOfCustomers().get(0), getListOfCustomers().get(1)));
    customersWithType.put(CUST_TYPE.GOOD, Arrays.asList(getListOfCustomers().get(2)));
    return customersWithType;
  }
  
  public static List<Shop> getShopsWithListOfCustomers(){
    return List.of(
        new Shop("Daves", getListOfCustomers()),
        new Shop("Lidl"),
        new Shop("Maypole", getListOfCustomers().stream()
            .filter(c -> c.getName().compareTo("Bob") != 0).collect(Collectors.toList())));
  }
  
  public static List<Shop> getShopsWithAtLeastOneBadCustomer(){
    List<Customer> badCustomers = List.of(
        new Customer("Bad Customer 1", CUST_TYPE.BAD)
          .addOrders(localOrders.entrySet().stream().map(o -> o.getValue()).collect(Collectors.toList())),
        new Customer("Bad Customer 2", CUST_TYPE.BAD)
          .addOrders(ordersAll.entrySet().stream().map(o -> o.getValue()).collect(Collectors.toList()))
        );
    
    Map<CUST_TYPE, List<Customer>> mapOfBadCustomers = new HashMap<>();
    mapOfBadCustomers.put(CUST_TYPE.BAD, badCustomers);
    
    return List.of(
        new Shop("Daves", mapOfCustomerTypeAndCustomers()),
        new Shop("Lidl"),
        new Shop("Maypole", mapOfBadCustomers)
        );
  }

  public static Map<Long, Order> localOrders = new HashMap<>();  
  public static Map<Long, Order> remoteOrders = new HashMap<>();
  public static Map<Long, Order> ordersAll = new HashMap<>();
  
  static {
    final long sampleSize = sampleNumbers.size();
    
    sampleNumbers.stream()
      .mapToLong(l -> l)
      .forEach(n -> {      
        localOrders.put(n, new Order(n));
        remoteOrders.put(n + sampleSize, new Order(n + sampleSize));
      });
    
    ordersAll.putAll(localOrders);
    ordersAll.putAll(remoteOrders);
  }
}
