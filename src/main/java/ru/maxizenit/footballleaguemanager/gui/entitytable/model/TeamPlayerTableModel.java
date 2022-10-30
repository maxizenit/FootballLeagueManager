package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.service.PlayerService;
import ru.maxizenit.footballleaguemanager.util.calculator.AgeCalculator;
import ru.maxizenit.footballleaguemanager.util.formatter.AgeFormatter;
import ru.maxizenit.footballleaguemanager.util.formatter.NameFormatter;

/**
 * Модель для таблицы игроков команды.
 */
public class TeamPlayerTableModel extends EntityTableModel<Player> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"№", "Поз", "Имя", "Возраст", "М", "Г", "ГП"};

  /**
   * Сервис для работы с игроками.
   */
  private final PlayerService playerService;

  public TeamPlayerTableModel(PlayerService playerService) {
    super(COLUMN_NAMES);
    this.playerService = playerService;
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, Player entity) {
    vector.add(entity.getNumber());
    vector.add(entity.getPosition().getCode());
    vector.add(NameFormatter.convert(entity));
    vector.add(AgeFormatter.format(AgeCalculator.calculate(entity.getBirthdate())));

    int matchesCount = 0;
    int goalsCount = 0;
    int assistsCount = 0;

    if (playerService != null) {
      matchesCount = playerService.getMatchCountByPlayer(entity);
      goalsCount = playerService.getGoalCountByPlayer(entity);
      assistsCount = playerService.getAssistCountByPlayer(entity);
    }

    vector.add(matchesCount);
    vector.add(goalsCount);
    vector.add(assistsCount);
  }
}
