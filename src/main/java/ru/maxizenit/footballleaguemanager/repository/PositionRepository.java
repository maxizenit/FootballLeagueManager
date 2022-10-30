package ru.maxizenit.footballleaguemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxizenit.footballleaguemanager.entity.Position;

/**
 * Репозиторий для позиций.
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

  List<Position> findAllByOrderByIndexNumber();
}
