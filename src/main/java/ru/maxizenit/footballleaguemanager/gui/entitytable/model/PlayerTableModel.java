package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.util.calculator.AgeCalculator;
import ru.maxizenit.footballleaguemanager.util.formatter.AgeFormatter;
import ru.maxizenit.footballleaguemanager.util.formatter.NameFormatter;

/**
 * Модель для таблицы игроков.
 */
public class PlayerTableModel extends EntityTableModel<Player> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Команда", "Поз", "Имя", "Возраст"};

  public PlayerTableModel() {
    super(COLUMN_NAMES);
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Player entity) {
    String teamName = entity.getTeam() != null ? entity.getTeam().getName() : null;
    vector.add(teamName);
    vector.add(entity.getPosition().getCode());
    vector.add(NameFormatter.convert(entity));
    vector.add(AgeFormatter.format(AgeCalculator.calculate(entity.getBirthdate())));
  }
}
