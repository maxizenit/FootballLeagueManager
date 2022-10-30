package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Position;

/**
 * Модель для таблицы позиций.
 */
public class PositionTableModel extends EntityTableModel<Position> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Порядок", "Код", "Название"};

  public PositionTableModel() {
    super(COLUMN_NAMES);
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Position entity) {
    vector.add(entity.getIndexNumber());
    vector.add(entity.getCode());
    vector.add(entity.getName());
  }
}
