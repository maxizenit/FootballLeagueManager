package ru.maxizenit.footballleaguemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Репозиторий для команд.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

  List<Team> findAllByOrderByName();
}