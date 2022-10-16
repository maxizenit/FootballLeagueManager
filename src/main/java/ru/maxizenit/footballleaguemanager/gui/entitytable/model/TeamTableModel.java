package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Модель для таблицы команд.
 */
public class TeamTableModel extends EntityTableModel<Team> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Код", "Название"};

  public TeamTableModel() {
    super(COLUMN_NAMES);
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Team entity) {
    vector.add(entity.getCode());
    vector.add(entity.getName());
  }
}
