package ru.maxizenit.footballleaguemanager.util.calculator;

import java.util.List;
import ru.maxizenit.footballleaguemanager.enm.Result;
import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Калькулятор результата матча для конкретной команды.
 */
public class MatchResultCalculator {

  /**
   * Возвращает результат матча для конкретной команды.
   *
   * @param match матч
   * @param team  команда, для которой рассчитывается результат
   * @return результат матча для конкретной команды
   */
  public static Result calculate(Match match, List<Goal> goals, Team team) {
    if (!Boolean.TRUE.equals(match.getPlayed())) {
      return Result.NO_RESULT;
    }

    Team technicalDefeatWinner = match.getTechnicalDefeatWinner();
    if (technicalDefeatWinner != null) {
      return technicalDefeatWinner.equals(team) ? Result.WIN : Result.LOSS;
    }

    int homeGoals = goals.stream().filter(g -> g.getTeam().equals(match.getHomeTeam())).toList().size();
    int guestGoals = goals.stream().filter(g -> g.getTeam().equals(match.getGuestTeam())).toList().size();

    if (homeGoals > guestGoals) {
      return match.getHomeTeam().equals(team) ? Result.WIN : Result.LOSS;
    }

    if (homeGoals < guestGoals) {
      return match.getHomeTeam().equals(team) ? Result.LOSS : Result.WIN;
    }

    return Result.DRAW;
  }
}