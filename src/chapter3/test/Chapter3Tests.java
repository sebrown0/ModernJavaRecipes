package chapter3.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import global_data.GlobalTestData;
import helpers.Order;
import utils.NumberManipulator;

class Chapter3Tests {

  /*
   * Reduction operations
   */
  @Test
  void reduceWithRange() {
    int i = IntStream.rangeClosed(1, 10).reduce(0, (a, b) -> (a + b));
    assertEquals(55, i);
  }

  @Test
  void reduceAndAdd() {
    BigDecimal bd1 = Stream.iterate(BigDecimal.ONE, f -> f.add(BigDecimal.ONE)).limit(10).reduce(BigDecimal.ONE,
        (acc, val) -> {
          return acc.add(val);
        });
    assertEquals(56, bd1.intValue());
  }

  @Test
  void countNumberOfWordsInString() {
    long l = Stream.of(GlobalTestData.sampleString).map(String::length).count();
    assertEquals(6, l);
  }

  @Test
  void maxWordLenInString() {
    long l = Stream.of(GlobalTestData.sampleString).mapToInt(String::length).max().orElse(0);
    assertEquals(7, l);
  }

  /*
   * String manipulation operations.
   */
  @Test
  void simpleAppend() {
    String s = Stream.of(GlobalTestData.sampleString)
        .collect(() -> new StringBuilder(), (sb, str) -> sb.append(str + ", "), (sb1, sb2) -> sb1.append(sb2))
        .toString();
    assertEquals("this, is, an, array, of, strings, ", s);
  }

  @Test
  void simpleAppendWithMethodReferences() {
    String s = Stream.of(GlobalTestData.sampleString).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
        .toString();
    assertEquals("thisisanarrayofstrings", s);
  }

  @Test
  void joiningWithCollector() {
    String s = Stream.of(GlobalTestData.sampleString).collect(Collectors.joining(", ")).toString();
    assertEquals("this, is, an, array, of, strings", s);
  }

  /*
   * Comparison operations.
   */
  @Test
  void compareStringByLen() {
    List<String> l = Stream.of(GlobalTestData.sampleString).sorted(Comparator.comparingInt(String::length))
        .collect(Collectors.toList());
    assertEquals("strings", l.get(5));
  }

  /*
   * Mapping operations.
   */
  @Test
  void doubleNumsAndSumDivisableBy3() {
    int i = IntStream.rangeClosed(1, 10).map(n -> n * 2).filter(n -> n % 3 == 0).sum();
    assertEquals(36, i);
  }

  /*
   * Collection operations.
   */
  @Test
  void partitionByOddAndEvenStringLen() {
    Map<Boolean, Long> m = GlobalTestData.sampleStrings.stream()
        .collect(Collectors.partitioningBy(s -> s.length() % 2 == 0, Collectors.counting()));
    assertEquals(4, m.get(true));
  }

  /*
   * Statistical operations.
   */
  @Test
  void getStatistics() {
    DoubleSummaryStatistics stats = DoubleStream.generate(Math::random).limit(10000).summaryStatistics();
    assertEquals(10000, stats.getCount());
  }

  /*
   * Filter operations.
   */
  @Test
  void simpleFindFirst1() {
    int i = GlobalTestData.sampleNumbers.stream().filter(n -> n == 5).findFirst().orElse(0);
    assertEquals(5, i);
  }

  @Test
  void simpleFindFirst2() {
    Integer i = GlobalTestData.sampleNumbers.stream().filter(n -> NumberManipulator.divisibleBy(n, 2.00))
        .map(n -> NumberManipulator.xplyBy(n, 4)).findFirst().orElse(0);
    assertEquals(8, i);
  }

  @Test
  void simpleFindAny() {
    Integer i = GlobalTestData.sampleNumbers.stream().filter(n -> NumberManipulator.divisibleBy(n, 7))
        .map(n -> NumberManipulator.xplyBy(n, 7)).findAny().orElse(0);
    assertTrue(i > 0);
  }

  @Test
  void flatMap1() {
    Long l = GlobalTestData.getListOfCustomers().stream()
      .flatMap(c -> c.getOrders().stream())
      .mapToLong(Order::getValue).sum();
    assertEquals(4500L, l);
  }
}
