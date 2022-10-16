package ru.maxizenit.footballleaguemanager.gui.matchcalendar;

import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;

import java.awt.Color;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.convert.Jsr310Converters.DateToLocalDateConverter;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.service.GoalService;
import ru.maxizenit.footballleaguemanager.util.calculator.MatchResultCalculator;
import ru.maxizenit.footballleaguemanager.util.formatter.ScoreFormatter;

/**
 * Политика выделения для календаря матчей.
 */
public class MatchCalendarDateHighlightPolicy implements DateHighlightPolicy {

  /**
   * Словарь дата матча - матч.
   */
  private final Map<LocalDate, Match> matchMap;

  /**
   * Команда.
   */
  private Team team;

  /**
   * Сервис для работы с голами.
   */
  private GoalService goalService;

  public MatchCalendarDateHighlightPolicy() {
    matchMap = new HashMap<>();
  }

  @Override
  public HighlightInformation getHighlightInformationOrNull(LocalDate localDate) {
    Match match = matchMap.get(localDate);

    if (match == null) {
      return null;
    }

    HighlightInformation info = new HighlightInformation(
        switch (MatchResultCalculator.calculate(match, goalService.getGoalsByMatch(match), team)) {
          case WIN -> Color.GREEN;
          case DRAW -> Color.YELLOW;
          case LOSS -> Color.RED;
          case NO_RESULT -> Color.GRAY;
        });

    info.tooltipText = ScoreFormatter.formatWithTeamCodes(match,
        goalService.getGoalsByMatch(match));

    return info;
  }

  /**
   * Заполняет политику выделения матчами.
   *
   * @param matches     матчи
   * @param team        команда
   * @param goalService сервис для работы с голами
   */
  public void setMatches(List<Match> matches, Team team, GoalService goalService) {
    matchMap.clear();
    this.team = team;
    this.goalService = goalService;
    matches.forEach(m -> matchMap.put(DateToLocalDateConverter.INSTANCE.convert(m.getDate()), m));
  }

  /**
   * Очищает политику выделения.
   */
  public void clear() {
    matchMap.clear();
    team = null;
  }
}
