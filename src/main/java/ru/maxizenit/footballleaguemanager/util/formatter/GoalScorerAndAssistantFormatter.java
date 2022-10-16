package ru.maxizenit.footballleaguemanager.util.formatter;

import ru.maxizenit.footballleaguemanager.entity.Goal;

/**
 * Форматер для вывода имени забившего гол и отдавшего голевой пас.
 */
public class GoalScorerAndAssistantFormatter {

  private static final String SCORER_AND_ASSISTANT_PATTERN = "%s (%s)";

  /**
   * Возвращает имя забившего гол игрока и ассистента при его наличии.
   *
   * @param goal гол
   * @return имя забившего гол игрока и ассистента при его наличии
   */
  public static String format(Goal goal) {
    if (goal.getAssistant() == null) {
      return NameFormatter.convert(goal.getScorer());
    }

    return String.format(SCORER_AND_ASSISTANT_PATTERN, NameFormatter.convert(goal.getScorer()),
        NameFormatter.convert(goal.getAssistant()));
  }
}