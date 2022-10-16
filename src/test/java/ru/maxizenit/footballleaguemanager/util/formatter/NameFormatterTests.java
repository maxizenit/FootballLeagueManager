package ru.maxizenit.footballleaguemanager.util.formatter;

import org.junit.jupiter.api.Test;
import ru.maxizenit.footballleaguemanager.entity.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameFormatterTests {

  @Test
  public void convertTest() {
    Player playerWithoutShortName = new Player();
    playerWithoutShortName.setLastName("Пузанов");
    playerWithoutShortName.setFirstName("Максим");

    assertEquals("М. Пузанов", NameFormatter.convert(playerWithoutShortName));

    Player playerWithShortName = new Player();
    playerWithShortName.setLastName("Пузанов");
    playerWithShortName.setFirstName("Максим");
    playerWithShortName.setShortName("Макси");

    assertEquals("Макси", NameFormatter.convert(playerWithShortName));
  }
}
