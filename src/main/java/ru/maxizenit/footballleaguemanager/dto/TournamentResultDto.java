package ru.maxizenit.footballleaguemanager.dto;

import java.util.Comparator;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Класс {@code TournamentResultDto} представляет запись в турнирной таблице для конкретной
 * команды.
 */
@Getter
@Setter
public class TournamentResultDto implements Comparable<TournamentResultDto> {

  /**
   * Команда.
   */
  private Team team;

  /**
   * Место.
   */
  private Integer place;

  /**
   * Количество сыгранных матчей.
   */
  private Integer played;

  /**
   * Количество заработанных очков.
   */
  private Integer points;

  /**
   * Количество побед.
   */
  private Integer wins;

  /**
   * Количество ничьих.
   */
  private Integer draws;

  /**
   * Количество поражений.
   */
  private Integer loses;

  /**
   * Количество забитых голов.
   */
  private Integer scored;

  /**
   * Количество пропущенных голов.
   */
  private Integer conceded;

  /**
   * Разница забитых и пропущенных голов.
   */
  private Integer goalDifference;

  @Override
  public int compareTo(@NonNull TournamentResultDto other) {
    return Comparator.comparing(TournamentResultDto::getPoints)
        .thenComparing(TournamentResultDto::getWins)
        .thenComparing(TournamentResultDto::getScored)
        .thenComparing(TournamentResultDto::getGoalDifference)
        .reversed().compare(this, other);
  }
}
