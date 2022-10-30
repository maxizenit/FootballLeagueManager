package ru.maxizenit.footballleaguemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxizenit.footballleaguemanager.entity.Position;
import ru.maxizenit.footballleaguemanager.repository.PositionRepository;

/**
 * Сервис для работы с позициями.
 */
@Service
public class PositionService extends AbstractService<Position, PositionRepository> {

  @Autowired
  public PositionService(PositionRepository repository) {
    super(repository);
  }

  @Override
  public List<Position> getAll() {
    return repository.findAllByOrderByIndexNumber();
  }
}
