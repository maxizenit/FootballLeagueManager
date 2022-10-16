package ru.maxizenit.footballleaguemanager.gui.entitytable.filter;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import org.springframework.util.StringUtils;
import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.entity.Position;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.exception.InvalidFieldException;
import ru.maxizenit.footballleaguemanager.util.calculator.AgeCalculator;
import ru.maxizenit.footballleaguemanager.util.validator.NameValidator;

/**
 * Фильтр для игроков.
 */
@Setter
public class PlayerScrollPaneTableFilter implements ScrollPaneTableFilter<Player> {

  /**
   * Команда.
   */
  private Team team;

  /**
   * Позиция.
   */
  private Position position;

  /**
   * Минимальный возраст (в годах).
   */
  private Integer minAge;

  /**
   * Максимальный возраст (в годах).
   */
  private Integer maxAge;

  /**
   * Фамилия.
   */
  private String lastName;

  /**
   * Имя.
   */
  private String firstName;

  /**
   * Краткое имя.
   */
  private String shortName;

  @Override
  public boolean isFiltered(Player player) throws InvalidFieldException {
    List<String> invalidNames = new ArrayList<>();

    if (StringUtils.hasText(firstName)) {
      if (!NameValidator.validate(firstName)) {
        invalidNames.add("имя");
      } else if (!firstName.equalsIgnoreCase(player.getFirstName())) {
        return false;
      }
    }

    if (StringUtils.hasText(lastName)) {
      if (!NameValidator.validate(lastName)) {
        invalidNames.add("фамилия");
      } else if (!lastName.equalsIgnoreCase(player.getLastName())) {
        return false;
      }
    }

    if (StringUtils.hasText(shortName)) {
      if (!NameValidator.validate(shortName)) {
        invalidNames.add("короткое имя");
      } else if (!shortName.equalsIgnoreCase(player.getShortName())) {
        return false;
      }
    }

    if (!invalidNames.isEmpty()) {
      throw new InvalidFieldException(invalidNames);
    }

    if (team != null && !team.equals(player.getTeam())) {
      return false;
    }

    if (position != null && !position.equals(player.getPosition())) {
      return false;
    }

    Integer playerAge = AgeCalculator.calculate(player.getBirthdate());

    if (minAge != null && playerAge < minAge) {
      return false;
    }

    return maxAge == null || playerAge <= maxAge;
  }

  @Override
  public boolean isEmpty() {
    if (team != null) {
      return false;
    }

    if (position != null) {
      return false;
    }

    if (minAge != null) {
      return false;
    }

    if (maxAge != null) {
      return false;
    }

    if (StringUtils.hasText(firstName)) {
      return false;
    }

    if (StringUtils.hasText(lastName)) {
      return false;
    }

    return !StringUtils.hasText(shortName);
  }
}
