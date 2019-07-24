package chapter5.tests;

import static global_data.GlobalTestData.passage;
import static global_data.GlobalTestData.sampleNumbers;
import static global_data.GlobalTestData.sampleString;
import static global_data.GlobalTestData.sampleStringWithNulls;
import static global_data.GlobalTestData.sampleStringWithNullsReplaced;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.ListUtils.DataAndListUtils.removeNullsFromList;
import static utils.ListUtils.DataAndListUtils.replaceNullsInList;
import static utils.NumberManipulator.sumListOfNums;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import utils.SimpleFunctions;
import utils.ListUtils.DataAndListUtils;


class Chapter5Tests {

  /**
   * String manipulation.
   */
	@Test
	void removeNullsFromStrings() {
	  List<String> stringsWithoutNulls = removeNullsFromList(Arrays.asList(sampleStringWithNulls));
		assertTrue(Objects.deepEquals(Arrays.asList(sampleString), stringsWithoutNulls));
	}
		
	@Test
  void replaceNullsInStrings() {
    List<String> stringsWithNullsReplaced = replaceNullsInList(Arrays.asList(sampleStringWithNulls), "TEXT");
    assertTrue(Objects.deepEquals(Arrays.asList(sampleStringWithNullsReplaced), stringsWithNullsReplaced));
  }
	
	@Test
	void removeFullStopTest() {
	  String s = SimpleFunctions.removeFullStop.apply("Hello.");
	  assertEquals("Hello", s);
	}

  @Test
  void removePunctuation() {
    String passageWithoutCommas = DataAndListUtils.removePunctuation(passage);
    assertTrue(passage.contains(","));
    assertFalse(passageWithoutCommas.contains(","));
  }
  
	@Test
  void checkForOccurancesOfWordInPassage1() {
	  long words = DataAndListUtils.countWord(passage, "thou");
	  assertEquals(2, words);
  }
	
	@Test
  void checkForOccurancesOfWordsInPassage2() {
    Map<String, Integer> words = DataAndListUtils.countWords(passage, "thou", "thy", "on");
    assertEquals(2, words.get("thou"));
  }
		
	@Test
	void checkForOccurancesOfWordsInPassage3() {
    Map<String, Integer> words = DataAndListUtils.fullWordCount(passage);
    assertEquals(1, words.get("son"));
	}
	
	 /**
   * Numbers.
   */
  @Test
  void sumListOfNumbers() {
    int value = sumListOfNums(sampleNumbers);
    assertEquals(55, value);
  }
  
  @Test
  void randomNumberList() {
    LinkedList<Integer> list = new Random().ints(5, 1, 10)
        .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
    assertTrue(sumListOfNums(list) <= 50);
  }

  /**
   * Compose and then.
   */
  @Test
  void andThen1() {
    int sqr = SimpleFunctions.squareStringLen.apply("Hello");
    assertEquals(25, sqr);
  }
  
  @Test
  void compose1() {
    String s = SimpleFunctions.addFullStop.apply("Hello");
    assertEquals("Hello.", s);
  }
  
  @Test
  void andThen2() {
    int sqr = SimpleFunctions.addToStringAndGetSquare.apply("Hello");
    assertEquals(36, sqr);
  }
  
  @Test
  void andThen3() {
    String s = SimpleFunctions.concatStringsAndTerminate.apply("Please add a full ", "stop to me");
    assertEquals("Please add a full stop to me.", s);
  }
  
  @Test
  void andThen4() {
    Logger logger = Logger.getLogger(this.getClass().getName());
    SimpleFunctions.printThenLog.accept("Consumer\n", logger);
  }
  
  @Test
  void andThen5() {
    Logger logger = Logger.getLogger(this.getClass().getName());
    SimpleFunctions.logAndPrint.apply("Function\n", logger);
  }
  
  /*
   * Predicate
   */
  @Test
  void isPerfectNumber() {
    assertTrue(SimpleFunctions.isPerfectSquare.test(16));
  }
  
  @Test
  void isNumberPerfectAndTriangular() {
    OptionalInt num = IntStream.rangeClosed(0, 1000)
      .filter(SimpleFunctions.numberisTriangularAndPerfect)
      .reduce(SimpleFunctions.sumInts);
    
    assertEquals(37, num.getAsInt());
  }
  
  /*
   * Exceptions.
   */
  @Test
  void withException() {
    List<Integer> l = DataAndListUtils.divideNumsInList(sampleNumbers, 0)
      .stream()
      .filter(SimpleFunctions.isNotZero)
      .collect(Collectors.toList());
    
    assertTrue(l.isEmpty());
  }
  
  @Test
  void withoutException() {
    List<Integer> l = DataAndListUtils.divideNumsInList(sampleNumbers, 1)
      .stream()
      .filter(SimpleFunctions.isNotZero)
      .collect(Collectors.toList());
    
    assertFalse(l.isEmpty());
  }
  
  @Test
  void checkedException() {
    List<String> list = DataAndListUtils.encodeValues("www", "web", "com");
    list.forEach(System.out::println);
  }
  
}
