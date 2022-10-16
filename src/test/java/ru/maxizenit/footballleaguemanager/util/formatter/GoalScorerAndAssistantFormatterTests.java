package ru.maxizenit.footballleaguemanager.util.formatter;

import org.junit.jupiter.api.Test;
import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.entity.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoalScorerAndAssistantFormatterTests {

  @Test
  public void formatTest() {
    Goal goal = new Goal();
    Player scorer = new Player();
    scorer.setFirstName("Иван");
    scorer.setLastName("Иванов");
    goal.setScorer(scorer);
    assertEquals("И. Иванов", GoalScorerAndAssistantFormatter.format(goal));

    Player assistant = new Player();
    assistant.setFirstName("Пётр");
    assistant.setLastName("Петров");
    goal.setAssistant(assistant);
    assertEquals("И. Иванов (П. Петров)", GoalScorerAndAssistantFormatter.format(goal));
  }
}
