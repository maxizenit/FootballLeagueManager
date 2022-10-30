package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.entity.Squad;
import ru.maxizenit.footballleaguemanager.util.formatter.NameFormatter;

/**
 * Модель для таблицы записей из составов.
 */
public class SquadTableModel extends EntityTableModel<Squad> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Поз", "Имя"};

  public SquadTableModel() {
    super(COLUMN_NAMES);
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Squad entity) {
    Player player = entity.getPlayer();
    vector.add(player.getPosition().getCode());
    vector.add(NameFormatter.convert(player));
  }
}
