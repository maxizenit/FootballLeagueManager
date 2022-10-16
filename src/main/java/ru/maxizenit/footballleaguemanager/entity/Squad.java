package ru.maxizenit.footballleaguemanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс {@code Squad} представляет запись из состава.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Squad {

  /**
   * Идентификатор.
   */
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Матч, которому принадлежит запись.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "match_id")
  private Match match;

  /**
   * Команда, которой принадлежит запись.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  /**
   * Игрок, которому принадлежит запись.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "player_id")
  private Player player;
}
