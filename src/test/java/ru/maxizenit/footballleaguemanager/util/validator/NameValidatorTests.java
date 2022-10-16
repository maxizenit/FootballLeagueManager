package ru.maxizenit.footballleaguemanager.util.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class NameValidatorTests {

  @Test
  public void validateTest() {
    String correct = "Максим";
    String incorrect = "М@кс1м";

    assertTrue(NameValidator.validate(correct));
    assertFalse(NameValidator.validate(incorrect));
  }
}
