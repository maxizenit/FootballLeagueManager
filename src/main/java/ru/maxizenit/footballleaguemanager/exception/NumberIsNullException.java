package ru.maxizenit.footballleaguemanager.exception;

/**
 * Сигнализирует о том, что номер не присвоен, хотя должен.
 */
public class NumberIsNullException extends RuntimeException {

  /**
   * Сообщение об ошибке.
   */
  private static final String MESSAGE = "Номер должен быть присвоен";

  public NumberIsNullException() {
    super(MESSAGE);
  }
}