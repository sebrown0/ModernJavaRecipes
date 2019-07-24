package utils.test;

import java.util.List;

import helpers.Book;
import helpers.CustomerGenericId;
import helpers.Order;
import utils.ListUtils.GenericId;

/**
 * Test data for util package.
 * 
 * @author Steve Brown
 *
 */
public class UtilTestData {
  
  public static List<CustomerGenericId<Integer>> getListOfCustomers(){
    CustomerGenericId<Integer> c1 = new CustomerGenericId<Integer>(new GenericId<>(1), "Bob");
    CustomerGenericId<Integer> c2 = new CustomerGenericId<Integer>(new GenericId<>(2), "Tom");
    CustomerGenericId<Integer> c3 = new CustomerGenericId<Integer>(new GenericId<>(3), "Sally");

    c1.addOrder(new Order(1L, 100L)).addOrder(new Order(2L, 200L)).addOrder(new Order(3L, 300L));
    c2.addOrder(new Order(4L, 400L)).addOrder(new Order(5L, 500L)).addOrder(new Order(6L, 600L));
    c3.addOrder(new Order(7L, 700L)).addOrder(new Order(8L, 800L)).addOrder(new Order(9L, 900L));
    
    return List.of(c1, c2, c3);
  }

  public static List<Book<Integer>> getListOfBooksInteger(){
    return List.of(
        new Book<Integer>(new GenericId<Integer>(1), "Modern Java Recipes", 49.99),
        new Book<Integer>(new GenericId<Integer>(2), "Java 8 in Action", 49.99),    
        new Book<Integer>(new GenericId<Integer>(3), "Java SE8 for the Really Impatient", 39.99),    
        new Book<Integer>(new GenericId<Integer>(4), "Functional Programming in Java", 27.64),    
        new Book<Integer>(new GenericId<Integer>(5), "Making Java Groovy", 45.99),    
        new Book<Integer>(new GenericId<Integer>(6), "Gradle Recipes for Android", 23.76));
  }
    
  public static List<Book<String>> getListOfBooksString(){
    return List.of(
        new Book<String>(new GenericId<String>("MJR"), "Modern Java Recipes", 49.99),
        new Book<String>(new GenericId<String>("JIA"), "Java 8 in Action", 49.99),    
        new Book<String>(new GenericId<String>("JRI"), "Java SE8 for the Really Impatient", 39.99),    
        new Book<String>(new GenericId<String>("FPR"), "Functional Programming in Java", 27.64),    
        new Book<String>(new GenericId<String>("MJG"), "Making Java Groovy", 45.99),    
        new Book<String>(new GenericId<String>("GRA"), "Gradle Recipes for Android", 23.76));
  }
  
}
