package ru.maxizenit.footballleaguemanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс {@code Goal} представляет гол.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Goal {

  /**
   * Идентификатор.
   */
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Матч, в котором забит гол.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "match_id")
  private Match match;

  /**
   * Команда, забившая гол.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  /**
   * Минута основного времени, на которой забит гол.
   */
  @Min(0)
  @Max(90)
  @NotNull
  private Integer minute;

  /**
   * Минута компенсированного времени, на которой забит гол.
   */
  @Min(0)
  private Integer injuryMinute;

  /**
   * Игрок, забивший гол.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "scorer_id")
  private Player scorer;

  /**
   * Игрок, отдавший голевую передачу для забившего гол.
   */
  @ManyToOne
  @JoinColumn(name = "assistant_id")
  private Player assistant;

  /**
   * Забит ли гол с пенальти.
   */
  private Boolean penalty;

  /**
   * Забит ли гол в свои ворота.
   */
  private Boolean ownGoal;
}
