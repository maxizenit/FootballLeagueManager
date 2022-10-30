package ru.maxizenit.footballleaguemanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FootballLeagueManagerApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(FootballLeagueManagerApplication.class).headless(false).run(args);
  }
}
