package chapter10.test;

import static global_data.GlobalTestData.getBlogs;
import static global_data.GlobalTestData.getListOfEmployees;
import static global_data.GlobalTestData.getShopsWithAtLeastOneBadCustomer;
import static global_data.GlobalTestData.getShopsWithListOfCustomers;
import static global_data.GlobalTestData.sampleNumbers;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static helpers.BlogPost.BlogPostType;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import chapter10.NameSupplier;
import chapter10.SumNums;
import chapter9.HelperMethods;
import helpers.BlogPost;
import helpers.Customer;
import helpers.Customer.CUST_TYPE;
import helpers.Employee;
import helpers.Shop;
import helpers.Tuple;
import utils.SimpleFunctions;

class Chapter10Tests {

  @Test
  void namesStream() {
    try (Stream<String> names = new NameSupplier().get()){
      String result = names.filter(n -> n.equals("Homer")).findFirst().orElse("");
      assertEquals("Homer", result);
    }
  }
  
  @Test
  void staticAndPrivateMethodsInAnInterface() {
    int result = SumNums.addEvens(1,2,3,4,5,6,7,8,9) + SumNums.addOdds(1,3,5,7,9);
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
          .collect(groupingBy(Shop::getShopName, mapping(Shop::getCustomers, toList())));
    
    assertFalse(shopsAndCustomers.keySet().isEmpty());
  }
  
  @Test
  void maxCustomerOrderForShop() {
    Map<String, List<Customer>> shopsAndCustomers = 
        getShopsWithListOfCustomers().stream()
        .collect(groupingBy(Shop::getShopName, flatMapping(HelperMethods::getStreamOfCustomers, toList())));

    List<Customer> davesCustomers = shopsAndCustomers.get("Daves");
    Long bobsMaxOrderAtDaves = davesCustomers.stream().filter(c -> c.getName().compareToIgnoreCase("bob") == 0)
      .flatMap(bob -> bob.getOrders().stream())
      .mapToLong(bobsOrders -> bobsOrders.getValue()).max().orElse(0);
    
    assertEquals(300, bobsMaxOrderAtDaves);
  }
  
  @Test
  void badCustomerDebtForShop() {
    Shop maypole = getShopsWithAtLeastOneBadCustomer().get(2);
    Long badDebt = maypole.getMapOfCustomerTypeAndCustomers().entrySet().stream() // Map<CUST_TYPE, List<Customer>> 
      .filter(k -> k.getKey().compareTo(CUST_TYPE.BAD) == 0)                      // Map<CUST_TYPE.BAD, List<Customer>>
      .flatMap(cust -> cust.getValue().stream())                                  // Stream<Customer>
      .flatMapToLong(order -> order.getOrders().stream()
                                .mapToLong(o -> o.getValue()))
      .sum();
    
    assertEquals(26500, badDebt);
  }
  
  @Test
  void badCustomerDebtForShop1() {
    Shop maypole = getShopsWithAtLeastOneBadCustomer().get(2);
    Long badDebt = maypole.getMapOfCustomerTypeAndCustomers().entrySet().stream() // Map<CUST_TYPE, List<Customer>> 
      .filter(k -> k.getKey().compareTo(CUST_TYPE.BAD) == 0)                      // Map<CUST_TYPE.BAD, List<Customer>>
      .flatMap(cust -> cust.getValue().stream())                                  // Stream<Customer>
      .flatMapToLong(HelperMethods::getOrderValue)
      .sum();
    
    assertEquals(26500, badDebt);
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
  
}
