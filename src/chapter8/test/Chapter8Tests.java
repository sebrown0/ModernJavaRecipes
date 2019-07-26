package chapter8.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import chapter8.DateAdjusters;
import chapter8.PayDayAdjuster;
import chapter8.ZoneUtility;

class Chapter8Tests {

  private static final int PAY_DAY = 15;
  private static final LocalDate DATE_TEST = LocalDate.of(2019, Month.JULY, 24);
  private static final LocalTime TIME_TEST = LocalTime.of(12, 00, 00);
  private static final LocalDateTime DATE_TIME_TEST = LocalDateTime.of(DATE_TEST, TIME_TEST);
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
  
  /*
   * Using zones.
   */
  @Test
  void zoneId() {
    String zone = ZoneId.getAvailableZoneIds().stream()
        .filter(z -> z.compareTo("Europe/Malta") == 0)
        .findFirst().orElse("None");
    assertEquals("Europe/Malta", zone);
  }

  @Test
  void atZone() {
    ZonedDateTime malta = DATE_TIME_TEST.atZone(ZoneId.of("Europe/Malta"));
    assertTrue(malta.toString().compareTo("2019-07-24T12:00+02:00[Europe/Malta]") == 0);
    ZonedDateTime london = malta.withZoneSameInstant(ZoneId.of("Europe/London"));
    assertEquals(-1, london.getHour() - malta.getHour());
  }
  
  /*
   *  Creating Dates and Times from Existing Instances.
   */
  @Test
  void addMonths() {
    assertEquals("APRIL", Month.JANUARY.plus(3).toString());
  }
  
  @Test
  void localDatePlus2Days() {
    LocalDate testDate = DATE_TEST.plusDays(2);
    assertEquals("2019-07-26", testDate.format(DATE_FORMATTER));
  }

  @Test
  void localTimePlus2Seconds() {
    LocalTime testTime = TIME_TEST.plusSeconds(2);
    assertEquals("12:00:02", testTime.format(TIME_FORMATTER));
  }
  
  @Test
  void localDatePlusPeriod() {
    Period period = Period.of(1, 1, 1);
    LocalDate testDate = DATE_TEST.plus(period);
    assertEquals("2020-08-25", testDate.format(DATE_FORMATTER));
  }
  
  @Test
  void localDateMinus1Century() {
    LocalDate testDate = DATE_TEST.minus(1, ChronoUnit.CENTURIES);
    assertEquals("1919-07-24", testDate.format(DATE_FORMATTER));
  }
  
  @Test
  void localDatePlus1Century() {
    LocalDate testDate = DATE_TEST.withYear(2119);
    assertEquals("2119-07-24", testDate.format(DATE_FORMATTER));
  }
  
  /*
   * Adjusters and queries
   */
  @Test
  void adjustFirstDayOfNextMonth() {
    LocalDate testDate = DATE_TEST.with(TemporalAdjusters.firstDayOfNextMonth());
    assertEquals("2019-08-01", testDate.format(DATE_FORMATTER));
  }
  
  @Test
  void firstPayDayOfTheMonth() {
    PayDayAdjuster payDayAdjuster = new PayDayAdjuster(PAY_DAY);
    IntStream.rangeClosed(1, PAY_DAY)
      .mapToObj(day -> DATE_TEST.withDayOfMonth(day))
      .forEach(date -> assertEquals(15, date.with(payDayAdjuster).getDayOfMonth()));    
  }
  
  @Test
  void lastPayDayOfTheMonth() {
    final int LAST_DAY_MONTH = DATE_TEST.lengthOfMonth();
    PayDayAdjuster payDayAdjuster = new PayDayAdjuster(PAY_DAY);
    IntStream.rangeClosed(PAY_DAY + 1, LAST_DAY_MONTH)
      .mapToObj(day -> DATE_TEST.withDayOfMonth(day))
      .forEach(date -> assertEquals(LAST_DAY_MONTH, date.with(payDayAdjuster).getDayOfMonth()));    
  }
  
  @Test
  void lastPayDayOfTheMonthWithMethodReference() {
    final int LAST_DAY_MONTH = DATE_TEST.lengthOfMonth();
    DateAdjusters.payDay(16);
    IntStream.rangeClosed(PAY_DAY + 1, LAST_DAY_MONTH)
      .mapToObj(day -> DATE_TEST.withDayOfMonth(day))
      .forEach(date -> assertEquals(LAST_DAY_MONTH, date.with(DateAdjusters::adjustPayDay).getDayOfMonth()));    
  }
  
  @Test
  void queries() {
    assertEquals(ChronoUnit.DAYS, LocalDate.now().query(TemporalQueries.precision()));
    assertEquals(ZoneId.systemDefault(), ZonedDateTime.now().query(TemporalQueries.zoneId()));
  }
  
  @Test
  void daysBetweenGood() {
    LocalDate now = LocalDate.now();
    DateAdjusters.eventDate(now.plus(2, ChronoUnit.DAYS));
    assertEquals(2, DateAdjusters.daysUntilAnEvent(now));
  }
  
  @Test
  void daysBetweenBad() {
    DateAdjusters.eventDate(DATE_TEST.minus(2, ChronoUnit.YEARS));
    assertEquals(0, DateAdjusters.daysUntilAnEvent(DATE_TEST));
  }

  @Test
  void daysBetweenZero() {
    DateAdjusters.eventDate(DATE_TEST);
    assertEquals(0, DateAdjusters.daysUntilAnEvent(DATE_TEST));
  }
  
  @Test
  void daysBetweenRange() {
    DateAdjusters.eventDate(DATE_TEST.plusYears(4));
    long maxDaysUntil = IntStream.rangeClosed(0, 4)
      .mapToObj(n -> DATE_TEST.plusYears(n))
      .mapToLong(DateAdjusters::daysUntilAnEvent)
      .max().orElse(0);
      
    assertEquals(1461, maxDaysUntil);
  }
  
  /*
   * Formatting
   */
  @Test
  void dateFormating() {
    String date;
    date = DATE_TEST.format(DATE_FORMATTER);
    assertEquals("2019-07-24", date);
    date = DATE_TEST.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    assertEquals("24/07/2019", date);
    date = DATE_TEST.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    assertEquals("Wednesday, 24 July 2019", date);
    date = DATE_TEST.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.ITALY));
    assertEquals("mercoledì 24 luglio 2019", date);
  }
  
  /*
   * Zones.
   */
  @Test
  void zoneOffsets() {
    Map<String, ZoneOffset> zones = ZoneUtility.MapZonesWithIrregularOffsets();
//    zones.forEach((k,v) -> System.out.println(String.format("Time zone [%s] Offset [%s]", k, v))); 
    assertEquals("+05:45", zones.get("Asia/Kathmandu").toString());
  }

  @Test
  void getZoneOffset() {
    assertEquals("+04:30", ZoneUtility.getOffsetForZone("Asia/Kabul"));
  }
  
  @Test
  void getRegionNames4OffsetWithZoneId() {
    try {
      List<String> regions = ZoneUtility.getRegionNamesForOffset(ZoneOffset.of("+05:45"));
      assertTrue(regions.contains("Asia/Kathmandu"));      
    } catch (Exception e) {
      fail("[getRegionNames4OffsetWithZoneId] Incorrect offset");
    }
  }
  
  @Test
  void getRegionNames4OffsetWithHourAndMin() {
    try {
    List<String> regions = ZoneUtility.getRegionNamesForOffset(14,0);
    assertTrue(regions.contains("Etc/GMT-14"));
    } catch (Exception e) {
      fail("[getRegionNames4OffsetWithHourAndMin] Incorrect offset");
    }
  }
}

