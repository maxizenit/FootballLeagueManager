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
 * Класс {@code Position} представляет позицию.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Position {

  /**
   * Идентификатор.
   */
  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Порядковый номер для сортировки.
   */
  @NotNull
  private Integer indexNumber;

  /**
   * Краткий код.
   */
  @NotNull
  @Pattern(regexp = "^[А-ЯЁ0-9]+$")
  @Size(min = 1)
  private String code;

  /**
   * Название.
   */
  @NotNull
  @Pattern(regexp = "^[а-яА-ЯёЁ ]+$")
  @Size(min = 1)
  private String name;

  @Override
  public String toString() {
    return code;
  }
}
