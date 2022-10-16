package ru.maxizenit.footballleaguemanager.gui.matchcalendar;

import com.github.lgooddatepicker.components.CalendarPanel;

import java.util.List;

import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.service.GoalService;

/**
 * Календарь матчей.
 */
public class MatchCalendar extends CalendarPanel {

  /**
   * Политика выделения ячеек.
   */
  private final MatchCalendarDateHighlightPolicy highlightPolicy;

  public MatchCalendar() {
    super();
    highlightPolicy = new MatchCalendarDateHighlightPolicy();
    getSettings().setHighlightPolicy(highlightPolicy);
  }

  /**
   * Заполняет календарь матчами команды.
   *
   * @param matches     матчи
   * @param team        команда
   * @param goalService сервис для работы с голами
   */
  public void setMatches(List<Match> matches, Team team, GoalService goalService) {
    highlightPolicy.setMatches(matches, team, goalService);
    updateUI();
  }

  @Override
  public void updateUI() {
    super.updateUI();
    drawCalendar();
  }

  /**
   * Очищает календарь.
   */
  public void clear() {
    highlightPolicy.clear();
    updateUI();
  }
}
