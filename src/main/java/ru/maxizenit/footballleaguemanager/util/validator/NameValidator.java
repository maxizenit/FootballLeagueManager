package ru.maxizenit.footballleaguemanager.util.validator;

import java.util.regex.Pattern;

/**
 * Валидатор для имён.
 */
public class NameValidator {

  /**
   * Паттерн для валидации.
   */
  private static final Pattern pattern = Pattern.compile("^[а-яА-ЯёЁ ]+$");

  /**
   * Возвращает {@code true}, если имя валидно. Имя не должно содержать ничего, кроме букв русского
   * алфавита и пробелов.
   *
   * @param name имя
   * @return {@code true}, если имя валидно
   */
  public static boolean validate(String name) {
    return pattern.matcher(name).matches();
  }
}
