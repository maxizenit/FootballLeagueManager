package ru.maxizenit.footballleaguemanager.util.calculator;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.Years;

/**
 * Калькулятор возраста игрока в годах.
 */
public class AgeCalculator {

  /**
   * Возвращает количество полных лет между указанной датой и сегодняшним днём.
   *
   * @param date дата
   * @return количество полных лет между указанной датой и сегодняшним днём
   */
  public static Integer calculate(Date date) {
    LocalDate birthDate = new LocalDate(date);
    LocalDate now = new LocalDate();

    return Years.yearsBetween(birthDate, now).getYears();
  }
}
