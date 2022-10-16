package ru.maxizenit.footballleaguemanager.util.formatter;

import ru.maxizenit.footballleaguemanager.entity.Player;

/**
 * Форматер имени игрока для табличных моделей и комбинированных списков.
 */
public class NameFormatter {

  private static final String FORMAT = "%s. %s";

  /**
   * Возвращает краткое имя, если оно есть, или строку из инициала имени и фамилии футболиста.
   *
   * @param player футболист
   * @return краткое имя, если оно есть, или строка из инициала имени и фамилии футболиста
   */
  public static String convert(Player player) {
    return player.getShortName() != null ? player.getShortName()
        : String.format(FORMAT, player.getFirstName().charAt(0), player.getLastName());
  }
}
