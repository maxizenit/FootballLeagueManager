package ru.maxizenit.footballleaguemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.maxizenit.footballleaguemanager.entity.Player;

/**
 * Репозиторий для игроков.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

  @Query("SELECT pl FROM Player pl LEFT JOIN Position pos ON pl.position.id = pos.id WHERE pl.team.id = :team_id ORDER BY pos.indexNumber, pl.number")
  List<Player> findByTeamId(@Param("team_id") Integer teamId);

  Player findByTeamIdAndNumber(Integer teamId, Integer number);
}
