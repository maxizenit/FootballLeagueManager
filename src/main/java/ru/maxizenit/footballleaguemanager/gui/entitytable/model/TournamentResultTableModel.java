package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.Vector;

import ru.maxizenit.footballleaguemanager.dto.TournamentResultDto;

/**
 * Модель для турнирной таблицы.
 */
public class TournamentResultTableModel extends EntityTableModel<TournamentResultDto> {

  /**
   * Названия столбцов.
   */
  private static final String[] COLUMN_NAMES = {"Место", "Команда", "М", "В", "Н", "П", "ГЗ", "ГП",
      "РГ", "О"};

  public TournamentResultTableModel() {
    super(COLUMN_NAMES);
  }

  @Override
  protected void fillVectorFromEntity(Vector<Object> vector, TournamentResultDto entity) {
    vector.add(entity.getPlace());
    vector.add(entity.getTeam().getName());
    vector.add(entity.getPlayed());
    vector.add(entity.getWins());
    vector.add(entity.getDraws());
    vector.add(entity.getLoses());
    vector.add(entity.getScored());
    vector.add(entity.getConceded());
    vector.add(entity.getGoalDifference());
    vector.add(entity.getPoints());
  }
}
