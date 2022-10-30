package ru.maxizenit.footballleaguemanager.service;

import java.util.List;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.exception.NumberIsBusyException;
import ru.maxizenit.footballleaguemanager.exception.NumberIsNullException;
import ru.maxizenit.footballleaguemanager.repository.PlayerRepository;

/**
 * Сервис для работы с игроками.
 */
@Service
public class PlayerService extends AbstractService<Player, PlayerRepository> {

  /**
   * Сервис для работы с голами.
   */
  private final GoalService goalService;

  /**
   * Сервис для работы с записями из составов.
   */
  private final SquadService squadService;

  @Autowired
  public PlayerService(PlayerRepository repository, GoalService goalService,
      SquadService squadService) {
    super(repository);
    this.goalService = goalService;
    this.squadService = squadService;
  }

  @Override
  public void save(@NonNull Player entity) {
    if (entity.getTeam() != null) {
      if (entity.getNumber() == null) {
        throw new NumberIsNullException();
      } else if (isNumberBusy(entity)) {
        throw new NumberIsBusyException();
      }
    }

    repository.save(entity);
  }

  /**
   * Возвращает количество матчей, сыгранных игроком.
   *
   * @param player игрок
   * @return количество матчей, сыгранных игроком
   */
  public int getMatchCountByPlayer(Player player) {
    return squadService.getSquadsByPlayer(player).stream()
        .filter(s -> Boolean.TRUE.equals(s.getMatch().getPlayed())).toList().size();
  }

  /**
   * Возвращает количество голов, забитых игроком.
   *
   * @param player игрок
   * @return количество голов, забитых игроком
   */
  public int getGoalCountByPlayer(Player player) {
    return goalService.getGoalsByScorer(player).size();
  }

  /**
   * Возвращает количество голевых передач, отданных игроков.
   *
   * @param player игрок
   * @return количество голевых передач, отданных игроков
   */
  public int getAssistCountByPlayer(@NonNull Player player) {
    return goalService.getGoalsByAssistant(player).size();
  }

  /**
   * Возвращает список игроков команды.
   *
   * @param team команда
   * @return список игроков команды
   */
  public List<Player> getPlayersByTeam(@NonNull Team team) {
    return repository.findByTeamId(team.getId());
  }

  /**
   * Возвращает {@code true}, если номер занят другим игроком в этой команде.
   *
   * @param player игрок
   * @return {@code true}, если номер занят другим игроком в этой команде
   */
  private boolean isNumberBusy(Player player) {
    Player playerWithThisNumber = repository.findByTeamIdAndNumber(player.getTeam().getId(),
        player.getNumber());

    if (playerWithThisNumber == null) {
      return false;
    }

    return !playerWithThisNumber.equals(player);
  }
}
