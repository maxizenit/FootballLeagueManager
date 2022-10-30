package ru.maxizenit.footballleaguemanager.util.formatter;

import java.util.List;
import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Форматер результата матча для таблицы матчей.
 */
public class ScoreFormatter {

  private static final String FORMAT = "%s:%s";

  private static final String FORMAT_WITH_TEAM_CODES = "%s %s %s";

  private static final String NO_GOAL = "-";

  /**
   * Возвращает счёт матча в формате "Г1:Г2".
   *
   * @param match матч, для которого форматируется счёт
   * @param goals голы матча
   * @return результат матча в формате "Г1:Г2"
   */
  public static String format(Match match, List<Goal> goals) {
    String firstGoals = formatGoal(match, match.getHomeTeam(), goals);
    String secondGoals = formatGoal(match, match.getGuestTeam(), goals);

    return String.format(FORMAT, firstGoals, secondGoals);
  }

  /**
   * Возвращает число забитых голов как строку, если оно есть, или "-".
   *
   * @param match матч
   * @param team  команда
   * @param goals голы матча
   * @return число забитых голов как строка, если оно есть, или "-"
   */
  private static String formatGoal(Match match, Team team, List<Goal> goals) {
    if (!Boolean.TRUE.equals(match.getPlayed())) {
      return NO_GOAL;
    }

    if (match.getTechnicalDefeatWinner() != null) {
      if (match.getTechnicalDefeatWinner().equals(match.getHomeTeam())) {
        return "3";
      } else {
        return "0";
      }
    }

    return Integer.toString(goals.stream().filter(g -> g.getTeam().equals(team)).toList().size());
  }

  /**
   * Возвращает счёт матча в формате "КОД1 Г1:Г2 КОД2".
   *
   * @param match матч, для которого форматируется счёт
   * @param goals голы матча
   * @return результат матча в формате "КОД1 Г1:Г2 КОД2"
   */
  public static String formatWithTeamCodes(Match match, List<Goal> goals) {
    String firstCode = match.getHomeTeam().getCode();
    String secondCode = match.getGuestTeam().getCode();
    return String.format(FORMAT_WITH_TEAM_CODES, firstCode, format(match, goals), secondCode);
  }
}
