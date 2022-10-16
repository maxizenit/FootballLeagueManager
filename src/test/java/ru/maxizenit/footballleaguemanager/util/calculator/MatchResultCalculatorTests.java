package ru.maxizenit.footballleaguemanager.util.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.maxizenit.footballleaguemanager.enm.Result;
import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Team;

public class MatchResultCalculatorTests {

  @Test
  public void calculateTests() {
    Match match = new Match();
    Team homeTeam = new Team();
    homeTeam.setId(1);
    Team guestTeam = new Team();
    guestTeam.setId(2);

    match.setHomeTeam(homeTeam);
    match.setGuestTeam(guestTeam);

    List<Goal> goals = new ArrayList<>();

    assertEquals(Result.NO_RESULT, MatchResultCalculator.calculate(match, goals, homeTeam));

    goals.add(createGoal(match, homeTeam));
    assertEquals(Result.NO_RESULT, MatchResultCalculator.calculate(match, goals, homeTeam));

    match.setPlayed(true);
    assertEquals(Result.WIN, MatchResultCalculator.calculate(match, goals, homeTeam));
    assertEquals(Result.LOSS, MatchResultCalculator.calculate(match, goals, guestTeam));

    goals.add(createGoal(match, guestTeam));
    assertEquals(Result.DRAW, MatchResultCalculator.calculate(match, goals, guestTeam));
  }

  private Goal createGoal(Match match, Team team) {
    Goal goal = new Goal();
    goal.setMatch(match);
    goal.setTeam(team);
    return goal;
  }
}
