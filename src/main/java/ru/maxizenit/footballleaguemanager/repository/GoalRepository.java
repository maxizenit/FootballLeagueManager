package ru.maxizenit.footballleaguemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxizenit.footballleaguemanager.entity.Goal;

/**
 * Репозиторий для голов.
 */
@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

  List<Goal> findByScorerId(Integer scorerId);

  List<Goal> findByAssistantId(Integer assistantId);

  List<Goal> findByMatchId(Integer matchId);

  List<Goal> findByMatchIdAndTeamId(Integer matchId, Integer teamId);
}
