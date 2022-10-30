package ru.maxizenit.footballleaguemanager.util.formatter;

import ru.maxizenit.footballleaguemanager.entity.Goal;

/**
 * Форматер времени гола.
 */
public class GoalMinuteFormatter {

  private static final String FORMAT = "%s+%s";

  /**
   * Возвращает время гола в формате "минута основного времени + минута компенсированного, если
   * есть"
   *
   * @param goal гол, для которого форматируется время
   * @return время гола в формате "минута основного времени + минута компенсированного"
   */
  public static String format(Goal goal) {
    return goal.getInjuryMinute() != null ? String.format(FORMAT, goal.getMinute(),
        goal.getInjuryMinute()) : goal.getMinute().toString();
  }
}
