package ru.maxizenit.footballleaguemanager.util.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateFormatterTests {

  @Test
  public void formatTest() throws ParseException {
    Date date = new SimpleDateFormat("dd.MM.yyyy").parse("10.10.2011");
    assertEquals("10.10.2011", DateFormatter.format(date));
  }
}
