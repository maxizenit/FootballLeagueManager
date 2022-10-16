package ru.maxizenit.footballleaguemanager.gui.entitytable.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.table.DefaultTableModel;

import ru.maxizenit.footballleaguemanager.exception.InvalidFieldException;
import ru.maxizenit.footballleaguemanager.gui.entitytable.filter.ScrollPaneTableFilter;

/**
 * Модель для таблицы сущностей.
 *
 * @param <T> класс сущности
 */
public abstract class EntityTableModel<T> extends DefaultTableModel {

  /**
   * Текущий список сущностей.
   */
  protected List<T> entities;

  /**
   * Список сущностей до фильтрации.
   */
  private List<T> oldEntities;

  public EntityTableModel(String[] columnNames) {
    super(null, columnNames);
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  /**
   * Заполняет вектор модели векторами объектов для сущности.
   */
  private void updateDataVector() {
    dataVector.clear();
    entities.forEach(e -> {
      Vector<Object> vector = new Vector<>();
      fillVectorFromEntity(vector, e);
      dataVector.add(vector);
    });
  }

  /**
   * Заполняет вектор объектов полями сущности.
   *
   * @param vector вектор
   * @param entity сущность
   */
  protected abstract void fillVectorFromEntity(Vector<Object> vector, T entity);

  /**
   * Возвращает сущность по её индексу.
   *
   * @param index индекс
   * @return сущность
   */
  public T getEntityByRow(int index) {
    try {
      return entities.get(index);
    } catch (NullPointerException | IndexOutOfBoundsException e) {
      return null;
    }
  }

  /**
   * Обновляет список сущностей.
   *
   * @param entities список сущностей
   */
  public void updateEntities(List<T> entities) {
    this.entities = entities;
    oldEntities = null;
    updateDataVector();
  }

  /**
   * Очищает модель.
   */
  public void clear() {
    entities = null;
    oldEntities = null;
    dataVector.clear();
  }

  /**
   * Применяет фильтр к модели.
   *
   * @param filter фильтр
   * @throws InvalidFieldException если поля фильтра заполнены некорректно
   */
  public void filter(ScrollPaneTableFilter<T> filter) throws InvalidFieldException {
    clearFilter();

    List<T> newEntities = new CopyOnWriteArrayList<>();

    entities.parallelStream().forEach(e -> {
      try {
        if (filter.isFiltered(e)) {
          newEntities.add(e);
        }
      } catch (InvalidFieldException ex) {
        throw new RuntimeException(ex);
      }
    });

    oldEntities = new ArrayList<>(entities);
    entities = newEntities;
    updateDataVector();
  }

  /**
   * Очищает фильтр.
   */
  public void clearFilter() {
    if (oldEntities != null) {
      updateEntities(oldEntities);
    }
  }
}