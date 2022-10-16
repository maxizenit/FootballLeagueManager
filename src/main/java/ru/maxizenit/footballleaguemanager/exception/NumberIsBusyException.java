package ru.maxizenit.footballleaguemanager.exception;

/**
 * Сигнализирует о том, что такой номер в команде занят.
 */
public class NumberIsBusyException extends RuntimeException {

  /**
   * Сообщение об ошибке.
   */
  private static final String MESSAGE = "Такой номер уже занят в этой команде";

  public NumberIsBusyException() {
    super(MESSAGE);
  }
}
