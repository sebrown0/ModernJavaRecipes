package utils.test;

import static global_data.GlobalTestData.palindrome;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.NumberManipulator.divisibleBy;
import static utils.NumberManipulator.xplyBy;
import static utils.PalindromeEvaluator.isPalindrome;
import static utils.test.UtilTestData.getListOfBooksInteger;
import static utils.test.UtilTestData.getListOfBooksString;
import static utils.test.UtilTestData.getListOfCustomers;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import helpers.Book;
import helpers.CustomerGenericId;
import utils.SimpleFunctions;
import utils.ListUtils.DataAndListUtils;
import utils.ListUtils.GenericId;
import utils.ListUtils.GenericListToMap;
import utils.ListUtils.IdAble;

class UtilTests {

  /*
   * Palindrome.
   */
	@Test
	void palindromeChecker_Positive() {
		assertTrue(isPalindrome(palindrome));		
	}

	@Test
	void palindromeChecker_Negative() {
		assertFalse(isPalindrome(String.format("a%sb", palindrome)));		
	}
	
	/*
	 * Numbers.
	 */
	@Test
	void multiplyNumbers() {
	  assertEquals(10, xplyBy(2, 5));
	}
	
	@Test
	void isNumberDivisableByArg() {
	  assertTrue(divisibleBy(10, 2));
	  assertFalse(divisibleBy(11, 2));
	}
	
	/*
	 * Generic Id.
	 */
	@Test
	void genericId() {
	  IdAble<Integer> id = new GenericId<Integer>(12);
	  assertEquals(12, id.getId());
	}
	
	@Test
  void genericIdEquals() {
    GenericId<Integer> i1 = new GenericId<>(2);
    IdAble<Integer> i2 = new GenericId<>(2);
    IdAble<String> f2 = new GenericId<>("2");
    assertTrue(i1.equals(i2));
    assertFalse(i1 == i2);
    assertFalse(i1.equals(f2));
	}
	
	/*
	 * Generic list to map.
	 */
	@Test
  void mapBooksInteger() {  
    Map<Integer, Book<Integer>> books = 
        new GenericListToMap<Integer, Book<Integer>>().getMapFromList(getListOfBooksInteger());
    assertEquals(27.64, books.get(4).getPrice());
  }
  
  @Test
  void mapBooksString() {  
    Map<String, Book<String>> books = 
        new GenericListToMap<String, Book<String>>().getMapFromList(getListOfBooksString());
    assertEquals(27.64, books.get("FPR").getPrice());
  }
  
  @Test
  void mapCustomerInteger() {  
    Map<Integer, CustomerGenericId<Integer>> customers = 
        new GenericListToMap<Integer, CustomerGenericId<Integer>>().getMapFromList(getListOfCustomers());
    assertEquals("Bob", customers.get(1).getName());
    assertEquals(200, customers.get(1).getOrders().get(1).getValue());
  }
  
  /*
   * Exception.
   */
  @Test
  void checkedException() {
    List<String> list = DataAndListUtils.encodeValues("www", "web", "com");
    
    String www = list.stream()
      .reduce("", (a,b) -> SimpleFunctions.concatStringsAndTerminate.apply(a,b));
    www = SimpleFunctions.removeFullStop.apply(www);
    assertEquals("www.web.com", www);
  }
  
}
