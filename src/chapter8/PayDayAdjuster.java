package chapter8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * Modified version of the PayDayAdjuster (Java Docs and Ken Kousen).
 * 
 * @author Steve Brown
 *
 */
public class PayDayAdjuster implements TemporalAdjuster{

  private final int PAY_DAY;
  
  public PayDayAdjuster(int payDay) {
    this.PAY_DAY = payDay;
  }

  @Override
  public Temporal adjustInto(Temporal temporal) {
    LocalDate date = LocalDate.from(temporal);
    return checkIfDayIsAWeekDay(date.withDayOfMonth(adjustPayDay(date)));
  }
  
  /**
   * Set the pay day to the correct value depending on the day of the month.
   * Either the specified day (PAY_DAY) or the last day of the month.
   * 
   * @param date : The date entered.
   * @return  The adjusted pay day.
   */
  private int adjustPayDay(LocalDate date) {
    return (date.getDayOfMonth() <= PAY_DAY) ? PAY_DAY : date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
  }
  
  /**
   * See if pay day falls on a weekend. If it does return the previous Friday.
   * @param date  : Pay day.
   * @return  The pay day adjusted for weekends.
   */
  private LocalDate checkIfDayIsAWeekDay(LocalDate date) {
    return (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) 
        ? date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)) : date; 
  }
}
