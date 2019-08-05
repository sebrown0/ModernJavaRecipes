package chapter10.test;

import static global_data.GlobalTestData.*;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import chapter10.Chapter10Helpers;
import chapter10.NameSupplier;
import chapter10.SumNums;
import chapter9.OrderDatabase;
import chapter9.OrderRetreiver;
import global_data.GlobalTestData;
import helpers.BlogPost;
import helpers.BlogPost.BlogPostType;
import helpers.CornerShop;
import helpers.Customer;
import helpers.Customer.CUST_TYPE;
import helpers.Employee;
import helpers.ModernShop;
import helpers.Tuple;
import utils.SimpleFunctions;

class Chapter10Tests {

  static {
    OrderRetreiver.setCacheOfOrders(GlobalTestData.localOrders);
    OrderRetreiver.setOrderDAO(new OrderDatabase(GlobalTestData.remoteOrders));
  }
  
  @Test
  void namesStream() {
    try (Stream<String> names = new NameSupplier().get()){
      String result = names.filter(n -> n.equals("Homer")).findFirst().orElse("");
      assertEquals("Homer", result);
    }
  }
  
  @Test
  void staticAndPrivateMethodsInAnInterface() {
    int result = SumNums.addEvens(1,2,3,4,5,6,7,8,9) + SumNums.addOdds(1,2,3,4,5,6,7,8,9);
    assertEquals(1+2+3+4+5+6+7+8+9, result);
  }
  
  @Test
  void immutableList() {
    assertThrows(UnsupportedOperationException.class, () -> sampleNumbers.add(666));
  }

  @Test
  void streamDropWhile() {
    List<Employee> sortedEmps = getListOfEmployees().stream()
        .sorted(SimpleFunctions::sortEmployeeBySalary)
        .collect(toList());
        
    String richEmps = IntStream.range(0, sortedEmps.size())
        .mapToObj(i -> sortedEmps.get(i))
        .dropWhile(e -> e.getSalary() < 150_000)
        .map(e -> e.getName())
        .reduce("", SimpleFunctions.concatEmpNames);

    assertEquals("JamieCerseiTywin", richEmps);        
  }
  
  @Test
  void streamTakeWhile() {
    List<Integer> numsBiggerThan90 = new Random().ints(500, 0, 100)
        .boxed()
        .sorted(Comparator.reverseOrder())
        .takeWhile(i -> i > 90)
        .collect(toList());
    
    assertFalse(numsBiggerThan90.stream().anyMatch(n -> n < 90));
  }
  
  @Test
  void customerType() {
    assertEquals("BAD", CUST_TYPE.getCustType(2).toString());
  }
  
  @Test
  void groupByAndMap() {
    Map<String, List<List<Customer>>> shopsAndCustomers = 
        getShopsWithListOfCustomers().stream()
          .collect(groupingBy(CornerShop::getShopName, mapping(CornerShop::getCustomers, toList())));
    
    assertFalse(shopsAndCustomers.keySet().isEmpty());
  }
  
  @Test
  void maxCustomerOrderForShop() {
    Map<String, List<Customer>> shopsAndCustomers = 
        getShopsWithListOfCustomers().stream()
        .collect(groupingBy(CornerShop::getShopName, flatMapping(Chapter10Helpers::getStreamOfCustomersFromList, toList())));

    List<Customer> davesCustomers = shopsAndCustomers.get("Daves");
    Long bobsMaxOrderAtDaves = davesCustomers.stream().filter(c -> c.getName().compareToIgnoreCase("bob") == 0)
      .flatMap(bob -> bob.getOrders().stream())
      .mapToLong(bobsOrders -> bobsOrders.getValue()).max().orElse(0);
    
    assertEquals(300, bobsMaxOrderAtDaves);
  }
  
  @Test
  void totalNumberOfCustomers() {
    long totalNumCustomers = getShopsWithAtLeastOneBadCustomer()
        .stream() 
        .map(s -> s.getMapOfCustomerTypeAndCustomers().entrySet().stream())
        .flatMap(originalMap -> originalMap.map(tuple -> tuple.getValue())) 
        .flatMap(customerList -> customerList.stream())                     
        .count();
          
    assertEquals(5, totalNumCustomers);
  }

  @Test
  void totalNumberOfBadCustomers() {
    long badCustomers = getShopsWithAtLeastOneBadCustomer()
        .stream() 
        .map(s -> s.getMapOfCustomerTypeAndCustomers().entrySet().stream())
        .flatMap(originalMap -> originalMap.map(tuple -> tuple.getValue()))
        .flatMap(customerList -> customerList.stream())                     
        .filter(p -> p.getThisCustomerType().equals(CUST_TYPE.BAD))
        .count();
    
    assertEquals(2, badCustomers);
  }
  
  @Test
  void badCustomerDebtForShop() {
    ModernShop maypole = getShopsWithAtLeastOneBadCustomer().get(2);
    Long badDebt = maypole.getMapOfCustomerTypeAndCustomers().entrySet().stream() // Map<CUST_TYPE, List<Customer>> 
      .filter(k -> k.getKey().compareTo(CUST_TYPE.BAD) == 0)                      // Map<CUST_TYPE.BAD, List<Customer>>
      .flatMap(cust -> cust.getValue().stream())                                  // Stream<Customer>
      .flatMapToLong(Chapter10Helpers::getOrderValue)
      .sum();
    
    assertEquals(26500, badDebt);
  }
  
  @Test
  void badCustomerDebtForShopAndCustomer() {
    ModernShop maypole = getShopsWithAtLeastOneBadCustomer().get(0);
    Long badDebt = maypole.getMapOfCustomerTypeAndCustomers().entrySet().stream() // Map<CUST_TYPE, List<Customer>> 
      .filter(k -> k.getKey().compareTo(CUST_TYPE.BAD) == 0)                      // Map<CUST_TYPE.BAD, List<Customer>>
      .flatMap(cust -> cust.getValue().stream())                                  // Stream<Customer>
      .filter(cust -> cust.getName().equals("Bob"))
      .flatMapToLong(Chapter10Helpers::getOrderValue)
      .sum();
    
    assertEquals(600, badDebt);
  }
    
  @Test
  void getCustomersFromShopUsingPolymorhpicGetCustomers() {
    ModernShop lidl = new ModernShop("Lidl @ Safi", mapOfCustomerTypeAndCustomers());
    Chapter10Helpers helper = new Chapter10Helpers();
    int numCustomers = helper.getCustomersForShop(lidl).size();
    
    assertEquals(3, numCustomers);
  }
  
  @Test
  void optionalBadCustomers() {
    ModernShop lidl = new ModernShop("Lidl @ Safi", mapOfCustomerTypeAndCustomers());
    Chapter10Helpers helper = new Chapter10Helpers();
    
    long badCustomers = helper.getCustomersForShop(lidl)
        .stream()
        .map(shop -> Chapter10Helpers.findCustomerByType(CUST_TYPE.BAD, shop))
        .filter(Optional::isPresent)
        .count();
    
    assertEquals(0, badCustomers);
  }
  
  @Test
  void orderWithXpleOptionals() {
    List<Long> orderIDs = List.of(3L, 12L, -1L);
        
    orderIDs.stream()
      .map(OrderRetreiver::getOrder)
      .forEach(order -> {
        assertEquals(order.getId(), orderIDs.stream().filter(id -> id.equals(order.getId())).findFirst().get());
        });
  }
  
  @Test
  void mapPostByType_AndThenGetHowManyBlogsOfTypeIsGuide() {
    Map<BlogPostType, List<BlogPost>> postsByType = getBlogs().stream()
        .collect(groupingBy(BlogPost::getType));
     
    Long numberOfGuidePosts = postsByType.entrySet().stream()
        .flatMap(e -> e.getValue().stream())
        .filter(k -> k.getType().compareTo(BlogPostType.GUIDE) == 0)
        .count();
    assertEquals(2, numberOfGuidePosts);
  }
  
  @Test
  void mapPostsByTupleAndAuthor_AndThenGetHowManyAreAuthoredByA1() {
    Map<Tuple, List<BlogPost>> postsByTupleAndAuthorIsA1 = getBlogs().stream()
        .filter(e -> e.getAuthor().contentEquals("A1"))
        .collect(groupingBy(post -> new Tuple(post.getType(), post.getAuthor())));
    
    postsByTupleAndAuthorIsA1.entrySet().stream()
        .flatMap(e -> e.getValue().stream())
        .forEach(p -> assertEquals("A1", p.getAuthor()));
  }
  
  @Test
  void daysUntil() {    
    long days = getDateRange().count();
    assertEquals(10, days);
  }

  @Test
  void specificDayInDateRange() {
    long days = getDateRange().filter(d -> d.getDayOfWeek().name().equalsIgnoreCase("monday")).count();
    assertEquals(2, days);
  }
}
