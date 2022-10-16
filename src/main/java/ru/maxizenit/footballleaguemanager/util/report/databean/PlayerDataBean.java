package ru.maxizenit.footballleaguemanager.util.report.databean;

import lombok.Getter;
import lombok.Setter;

/**
 * Датабин для отчёта игроков.
 */
@Getter
@Setter
public class PlayerDataBean {

  /**
   * Позиция.
   */
  private String position;

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

  /**
   * Дата рождения.
   */
  private String birthdate;
}
