package ru.maxizenit.footballleaguemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.repository.MatchRepository;

/**
 * Сервис для работы с матчами.
 */
@Service
public class MatchService extends AbstractService<Match, MatchRepository> {

  @Autowired
  public MatchService(MatchRepository repository) {
    super(repository);
  }

  /**
   * Возвращает список матчей, в которых сыграла или сыграет команда.
   *
   * @param team команда
   * @return список матчей, в которых сыграла или сыграет команда.
   */
  public List<Match> getMatchesByTeam(Team team) {
    List<Match> matches = new ArrayList<>();

    matches.addAll(repository.findByHomeTeamId(team.getId()));
    matches.addAll(repository.findByGuestTeamId(team.getId()));

    return matches;
  }
}
