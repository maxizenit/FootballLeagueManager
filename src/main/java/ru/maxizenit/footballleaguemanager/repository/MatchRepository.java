package ru.maxizenit.footballleaguemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxizenit.footballleaguemanager.entity.Match;

/**
 * Репозиторий для матчей.
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

  List<Match> findByHomeTeamId(Integer firstTeamId);

  List<Match> findByGuestTeamId(Integer secondTeamId);
}
