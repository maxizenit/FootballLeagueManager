package ru.maxizenit.footballleaguemanager.util.formatter;

import org.junit.jupiter.api.Test;
import ru.maxizenit.footballleaguemanager.entity.Goal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoalMinuteFormatterTests {

  @Test
  public void formatTest() {
    Goal goal = new Goal();
    goal.setMinute(45);
    assertEquals("45", GoalMinuteFormatter.format(goal));

    goal.setInjuryMinute(4);
    assertEquals("45+4", GoalMinuteFormatter.format(goal));
  }
}
