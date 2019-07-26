package chapter8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

/**
 * Modified version of the PayDayAdjuster (Java Docs, Steve Brown and Ken Kousen).
 * Uses static methods so we can use method references.
 * 
 * @author Steve Brown
 *
 */
public class DateAdjusters {

  private static int PAY_DAY;
  private static LocalDate EVENT_DATE;
  
  private DateAdjusters() {}
  
  public static void payDay(int payDay) {
    PAY_DAY = payDay;
  }

  public static void eventDate(LocalDate eventDate) {
    EVENT_DATE = eventDate;
  }

  /**
   * Adjust the pay day to either:
   *  1. 1st pay day of the month.
   *  2. Last day of the month.
   *  3. Or the friday before either of 1 or 2 if that day is on a weekend.
   * 
   * @param temporal : The current date.
   * @return The adjusted pay day.
   */
  public static Temporal adjustPayDay(Temporal temporal) {
    LocalDate date = LocalDate.from(temporal);
    return checkIfDayIsAWeekDay(date.withDayOfMonth(getPayDayFor(date)));
  }
  
  /**
   * Set the pay day to the correct value depending on the day of the month.
   * Either the specified day (PAY_DAY) or the last day of the month.
   * If the specified pay day is not a valid day for that month then return the last day of the month.
   * 
   * @param date : The date entered.
   * @return  The adjusted pay day.
   */
  private static int getPayDayFor(LocalDate date) {
    if (payDayIsValid(date) && date.getDayOfMonth() <= PAY_DAY && PAY_DAY >= LocalDate.now().getDayOfMonth()) 
      return PAY_DAY; 
    else 
      return lastDayOfTheMonth(date);
  }
  
  /**
   * Check if the pay day is valid for the date (month) given.
   * @param date : The date.
   * @return  True if PAY_DAY is in the range of the days in the month.
   */
  private static boolean payDayIsValid(LocalDate date) {
    return (PAY_DAY > 0 && PAY_DAY <= date.lengthOfMonth()) ? true : false;
  }
  
  /**
   * Caculate the last day of the month for a given date.
   * 
   * @param date : The date.
   * @return  The last day of the month.
   */
  private static int lastDayOfTheMonth(LocalDate date) {
    return date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
  }
  
  /**
   * See if pay day falls on a weekend. If it does return the previous Friday.
   * @param date  : Pay day.
   * @return  The pay day adjusted for weekends.
   */
  private static LocalDate checkIfDayIsAWeekDay(LocalDate date) {
    return (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) 
        ? date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)) : date; 
  }
  
  /**
   * Calculate the number of days until a scheduled event EVENT_DATE.
   *  If the EVENT_DATE is in the past return 0;
   *  If the input date is after the event date assume it's for next year and calculate that.
   * 
   * @param inputDate : The start date.
   * @return The number of days between the argument and the END_DATE. 
   */
  public static long daysUntilAnEvent(Temporal inputDate) {
    long numDays = 0;    
    LocalDate startDate = LocalDate.from(inputDate);
    
    if(EVENT_DATE.isAfter(LocalDate.now())) {
      if(startDate.isAfter(EVENT_DATE)) {
        EVENT_DATE = EVENT_DATE.plusYears(1);
      }
      numDays = ChronoUnit.DAYS.between(inputDate, EVENT_DATE);
    }
    return numDays;
  }
}
