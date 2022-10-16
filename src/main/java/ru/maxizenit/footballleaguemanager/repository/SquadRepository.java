package ru.maxizenit.footballleaguemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxizenit.footballleaguemanager.entity.Squad;

/**
 * Репозиторий для записей из составов.
 */
@Repository
public interface SquadRepository extends JpaRepository<Squad, Integer> {

  List<Squad> findByPlayerId(Integer playerId);

  List<Squad> findByMatchIdAndTeamId(Integer matchId, Integer teamId);
}
