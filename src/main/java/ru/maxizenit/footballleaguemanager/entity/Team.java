package ru.maxizenit.footballleaguemanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс {@code Team} представляет команду.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Team {

  /**
   * Идентификатор.
   */
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Краткий код.
   */
  @NotNull
  @Pattern(regexp = "^[А-ЯЁ0-9]+$")
  @Size(min = 3, max = 3)
  private String code;

  /**
   * Название.
   */
  @NotNull
  @Size(min = 3)
  private String name;

  @Override
  public String toString() {
    return name;
  }
}
