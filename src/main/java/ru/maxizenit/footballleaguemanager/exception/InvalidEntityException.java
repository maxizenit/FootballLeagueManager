package ru.maxizenit.footballleaguemanager.exception;

/**
 * Сигнализирует о том, что сущность невалидна.
 */
public class InvalidEntityException extends Exception {

  public InvalidEntityException(String message) {
    super(message);
  }
}
