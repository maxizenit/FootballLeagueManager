package ru.maxizenit.footballleaguemanager.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.maxizenit.footballleaguemanager.util.formatter.NameFormatter;

/**
 * Класс {@code Player} представляет игрока.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Player {

  /**
   * Идентификатор.
   */
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Команда, в которой играет игрок.
   */
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  /**
   * Позиция.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "position_id")
  private Position position;

  /**
   * Номер.
   */
  private Integer number;

  /**
   * Имя.
   */
  @NotNull
  @Pattern(regexp = "^[а-яА-ЯёЁ ]+$")
  private String firstName;

  /**
   * Фамилия.
   */
  @NotNull
  @Pattern(regexp = "^[а-яА-ЯёЁ ]+$")
  private String lastName;

  /**
   * Краткое имя.
   */
  private String shortName;

  /**
   * Дата рождения.
   */
  @NotNull
  private Date birthdate;

  @Override
  public String toString() {
    return NameFormatter.convert(this);
  }
}
