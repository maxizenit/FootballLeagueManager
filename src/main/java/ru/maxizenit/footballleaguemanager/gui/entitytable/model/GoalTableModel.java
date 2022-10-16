package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.util.formatter.GoalMinuteFormatter;
import ru.maxizenit.footballleaguemanager.util.formatter.GoalScorerAndAssistantFormatter;

/**
 * Модель для таблицы голов.
 */
public class GoalTableModel extends EntityTableModel<Goal> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Забил (отдал)", "Минута"};

  public GoalTableModel() {
    super(COLUMN_NAMES);
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Goal entity) {
    vector.add(GoalScorerAndAssistantFormatter.format(entity));
    vector.add(GoalMinuteFormatter.format(entity));
  }
}
