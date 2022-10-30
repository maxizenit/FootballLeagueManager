package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.service.GoalService;
import ru.maxizenit.footballleaguemanager.util.formatter.DateFormatter;
import ru.maxizenit.footballleaguemanager.util.formatter.ScoreFormatter;

/**
 * Модель для таблицы матчей.
 */
public class MatchTableModel extends EntityTableModel<Match> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Дата", "Хозяева", "Счёт", "Гости"};

  /**
   * Сервис для работы с голами.
   */
  private final GoalService goalService;

  public MatchTableModel(GoalService goalService) {
    super(COLUMN_NAMES);
    this.goalService = goalService;
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Match entity) {
    vector.add(DateFormatter.format(entity.getDate()));
    vector.add(entity.getHomeTeam());
    vector.add(ScoreFormatter.format(entity, goalService.getGoalsByMatch(entity)));
    vector.add(entity.getGuestTeam());
  }
}
