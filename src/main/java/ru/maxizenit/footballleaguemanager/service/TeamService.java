package ru.maxizenit.footballleaguemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.repository.TeamRepository;

/**
 * Сервис для работы с командами.
 */
@Service
public class TeamService extends AbstractService<Team, TeamRepository> {

  @Autowired
  public TeamService(TeamRepository repository) {
    super(repository);
  }

  @Override
  public List<Team> getAll() {
    return repository.findAllByOrderByName();
  }
}
