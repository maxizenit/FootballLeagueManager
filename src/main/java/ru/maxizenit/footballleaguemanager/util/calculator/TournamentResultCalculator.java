package ru.maxizenit.footballleaguemanager.util.calculator;

import java.util.ArrayList;
import java.util.List;

import ru.maxizenit.footballleaguemanager.dto.TournamentResultDto;
import ru.maxizenit.footballleaguemanager.enm.Result;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.service.GoalService;

/**
 * Калькулятор турнирной таблицы.
 */
public class TournamentResultCalculator {

  /**
   * Возвращает список записей в турнирной таблице для всех команд.
   *
   * @param teams             список команд
   * @param teamMatchesGetter указатель на метод получения матчей команды
   * @param goalService       сервис для работы с голами
   * @return список записей в турнирной таблице для всех команд
   */
  public static List<TournamentResultDto> calculate(List<Team> teams,
      TeamMatchesGetter teamMatchesGetter, GoalService goalService) {
    List<TournamentResultDto> result = new ArrayList<>();

    for (Team team : teams) {
      result.add(calculateForTeam(team, teamMatchesGetter.getMatchesByTeam(team), goalService));
    }
    result = result.stream().sorted().toList();

    for (int i = 0; i < teams.size(); ++i) {
      result.get(i).setPlace(i + 1);
    }

    return result;
  }

  /**
   * Возвращает список записей в турнирной таблице для команды.
   *
   * @param team        команда
   * @param matches     список матчей команды
   * @param goalService сервис для работы с голами
   * @return список записей в турнирной таблице для всех команд
   */
  private static TournamentResultDto calculateForTeam(Team team, List<Match> matches,
      GoalService goalService) {
    TournamentResultDto result = new TournamentResultDto();
    result.setTeam(team);

    int played = matches.size();
    int wins = 0;
    int draws = 0;
    int loses = 0;
    int scored = 0;
    int conceded = 0;

    for (Match match : matches) {
      Result matchResult = MatchResultCalculator.calculate(match,
          goalService.getGoalsByMatch(match), team);

      switch (matchResult) {
        case WIN -> ++wins;
        case DRAW -> ++draws;
        case LOSS -> ++loses;
        case NO_RESULT -> --played;
      }

      if (!Result.NO_RESULT.equals(matchResult)) {
        if (team.equals(match.getTechnicalDefeatWinner())) {
          scored += 3;
        } else {
          if (match.getHomeTeam().equals(team)) {
            scored += goalService.getGoalsByMatchAndTeam(match, match.getHomeTeam()).size();
            conceded += goalService.getGoalsByMatchAndTeam(match, match.getGuestTeam()).size();
          } else {
            scored += goalService.getGoalsByMatchAndTeam(match, match.getGuestTeam()).size();
            conceded += goalService.getGoalsByMatchAndTeam(match, match.getHomeTeam()).size();
          }
        }
      }
    }

    result.setPlayed(played);
    result.setWins(wins);
    result.setDraws(draws);
    result.setLoses(loses);
    result.setScored(scored);
    result.setConceded(conceded);
    result.setPoints(3 * wins + draws);
    result.setGoalDifference(scored - conceded);

    return result;
  }
}
