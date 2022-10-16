package ru.maxizenit.footballleaguemanager.util.formatter;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.convert.Jsr310Converters.DateToLocalDateConverter;

/**
 * Форматер для даты.
 */
public class DateFormatter {

  private static final String PATTERN = "dd.MM.yyyy";

  /**
   * Возвращает дату как строку в формате "dd.MM.yyyy".
   *
   * @param date дата
   * @return дата как строка в формате "dd.MM.yyyy"
   */
  public static String format(Date date) {
    return DateToLocalDateConverter.INSTANCE.convert(date)
        .format(DateTimeFormatter.ofPattern(PATTERN));
  }
}
