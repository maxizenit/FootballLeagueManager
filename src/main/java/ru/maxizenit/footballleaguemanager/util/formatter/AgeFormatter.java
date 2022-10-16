package ru.maxizenit.footballleaguemanager.util.formatter;

/**
 * Форматер для возраста.
 */
public class AgeFormatter {

  private static final String FORMAT = "%s %s";

  private static final String YEAR = "год";

  private static final String YEAR_WITH_A = "года";

  private static final String YEARS = "лет";


  /**
   * Возвращает возраст как количество лет и существительное.
   *
   * @param yearsCount количество лет
   * @return строка в формате "количество лет + существительное"
   */
  public static String format(Integer yearsCount) {
    String title = switch (yearsCount % 10) {
      case 1 -> YEAR;
      case 2, 3, 4 -> YEAR_WITH_A;
      default -> YEARS;
    };

    return String.format(FORMAT, yearsCount, title);
  }
}