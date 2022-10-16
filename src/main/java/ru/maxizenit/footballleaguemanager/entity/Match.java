package ru.maxizenit.footballleaguemanager.entity;

import java.util.Date;
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
 * Класс {@code Match} представляет матч.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Match {

  /**
   * Идентификатор.
   */
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Дата проведения матча.
   */
  @NotNull
  private Date date;

  /**
   * Домашняя команда.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "home_team_id")
  private Team homeTeam;

  /**
   * Гостевая команда.
   */
  @NotNull
  @ManyToOne
  @JoinColumn(name = "guest_team_id")
  private Team guestTeam;

  /**
   * Сыгран ли матч.
   */
  private Boolean played;

  /**
   * Команда, которая выиграла матч из-за технического поражения противника.
   */
  @ManyToOne
  @JoinColumn(name = "technical_defeat_winner_id")
  private Team technicalDefeatWinner;
}
