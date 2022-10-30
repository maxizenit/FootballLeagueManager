package ru.maxizenit.footballleaguemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.entity.Squad;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.repository.SquadRepository;

/**
 * Сервис для работы с записями из составов.
 */
@Service
public class SquadService extends AbstractService<Squad, SquadRepository> {

  @Autowired
  public SquadService(SquadRepository repository) {
    super(repository);
  }

  /**
   * Возвращает список записей из составов для игрока.
   *
   * @param player игрок
   * @return список записей из составов для игрока
   */
  public List<Squad> getSquadsByPlayer(Player player) {
    return repository.findByPlayerId(player.getId());
  }

  /**
   * Возвращает список записей из составов для команды в матче.
   *
   * @param match матч
   * @param team  команда
   * @return список записей из состава для команды в матче
   */
  public List<Squad> getSquadsByMatchAndTeam(Match match, Team team) {
    return repository.findByMatchIdAndTeamId(match.getId(), team.getId());
  }
}
