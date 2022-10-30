package ru.maxizenit.footballleaguemanager.util.calculator;

import java.util.List;

import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Функциональный интерфейс, предоставляющий метод получения матчей команды.
 */
@FunctionalInterface
public interface TeamMatchesGetter {

  /**
   * Возвращает список матчей команды.
   *
   * @param team команда
   * @return список матчей команды
   */
  List<Match> getMatchesByTeam(Team team);
}
