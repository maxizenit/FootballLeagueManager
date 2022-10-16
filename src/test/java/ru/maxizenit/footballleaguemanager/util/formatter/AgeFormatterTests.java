package ru.maxizenit.footballleaguemanager.util.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AgeFormatterTests {

  @Test
  public void formatTest() {
    assertEquals("21 год", AgeFormatter.format(21));
    assertEquals("22 года", AgeFormatter.format(22));
    assertEquals("23 года", AgeFormatter.format(23));
    assertEquals("25 лет", AgeFormatter.format(25));
  }
}
