package ru.maxizenit.footballleaguemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.repository.GoalRepository;

/**
 * Сервис для работы с голами.
 */
@Service
public class GoalService extends AbstractService<Goal, GoalRepository> {

  @Autowired
  public GoalService(GoalRepository repository) {
    super(repository);
  }

  /**
   * Возвращает список голов игрока.
   *
   * @param scorer игрок, забивший голы
   * @return список голов игрока
   */
  public List<Goal> getGoalsByScorer(Player scorer) {
    return repository.findByScorerId(scorer.getId()).stream()
        .filter(g -> !Boolean.TRUE.equals(g.getOwnGoal())).toList();
  }

  /**
   * Возвращает список голов, в которых игрок участвовал как ассистент.
   *
   * @param assistant игрок, отдавший голевые передачи
   * @return список голов, в которых игрок участвовал как ассистент
   */
  public List<Goal> getGoalsByAssistant(Player assistant) {
    return repository.findByAssistantId(assistant.getId());
  }

  /**
   * Возвращает список голов, забитых в матче.
   *
   * @param match матч
   * @return список голов, забитых в матче
   */
  public List<Goal> getGoalsByMatch(Match match) {
    return repository.findByMatchId(match.getId());
  }

  /**
   * Возвращает список голов, которые команда забила в матче.
   *
   * @param match матч
   * @param team  команда
   * @return список голов, которые команда забила в матче
   */
  public List<Goal> getGoalsByMatchAndTeam(Match match, Team team) {
    return repository.findByMatchIdAndTeamId(match.getId(), team.getId());
  }
}
