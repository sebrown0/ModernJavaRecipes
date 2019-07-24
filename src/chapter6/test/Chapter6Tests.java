package chapter6.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.test.UtilTestData.getListOfBooksInteger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import helpers.Book;
import helpers.Company;
import helpers.Department;
import helpers.Employee;
import helpers.Manager;
import utils.ListUtils.GenericListToMap;

class Chapter6Tests {

  @Test
  void optional_1() {
    Map<Integer, Book<Integer>> books = 
        new GenericListToMap<Integer, Book<Integer>>().getMapFromList(getListOfBooksInteger());
    
    Optional<String> opStr = books.entrySet().stream()
        .filter(s -> s.getValue().getName().compareTo("anotherString") == 0)
        .map(s -> s.getValue().getName())
        .findFirst();
    
    assertEquals("Oh no!", opStr.orElse("Oh no!"));
  }
  
  @Test
  void optional_2() {
    Map<Integer, Book<Integer>> books = 
        new GenericListToMap<Integer, Book<Integer>>().getMapFromList(getListOfBooksInteger());
    
    Book<Integer> bookResult = books.entrySet().stream()
        .filter(s -> s.getValue().getName().compareTo("!+£$%^&*()") == 0)
        .map(s -> s.getValue())
        .findFirst().orElseGet(Book::new);
    
    assertEquals("Default Book Name", bookResult.getName());
  }

  @Test
  void optional_3() {
    Company c = new Company();
    Department d = new Department();
    Manager m = new Manager();
    m.setName("Big Boss");
    d.setBoss(m);
    c.setDepartment(d);
    
    String bossName = c.getDepartment()
        .flatMap(Department::getBoss)
        .map(Manager::getName).orElse("");
    
    assertEquals("Big Boss", bossName);
  }
  
  @Test
  void optionalFindEmployee() {
    Department d = new Department();
    String empName = d.findEmployeeById(1).orElse(new Employee()).getName();
    assertEquals("Cersei", empName);
  }
  
  @Test
  void optionalNoEmployee() {
    Department d = new Department();
    String empName = d.findEmployeeById(111).orElse(new Employee()).getName();
    assertEquals("name", empName);
  }
  
  @Test
  void optionalFindEmployees_1() {
    Department d = new Department();
    List<Employee> emps = d.findEmployeesByID_1(List.of(1,2,3));
    assertEquals("Jamie", emps.get(1).getName());
  }
  
  @Test
  void optionalFindEmployees_2() {
    Department d = new Department();
    List<Employee> emps = d.findEmployeesByID_2(List.of(1,2,3));
    assertEquals("Jamie", emps.get(1).getName());
  }
}
