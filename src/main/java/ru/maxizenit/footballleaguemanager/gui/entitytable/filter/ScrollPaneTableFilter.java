package ru.maxizenit.footballleaguemanager.gui.entitytable.filter;

import ru.maxizenit.footballleaguemanager.exception.InvalidFieldException;

/**
 * Интерфейс фильтра сущностей для {@code ScrollPaneTable}.
 *
 * @param <T> класс сущности
 */
public interface ScrollPaneTableFilter<T> {

    /**
     * Возвращает признак фильтрации сущности.
     *
     * @param t сущность
     * @return {@code true}, если сущность удовлетворяет фильтру
     * @throws InvalidFieldException если поля фильтра заполнены некорректно
     */
    boolean isFiltered(T t) throws InvalidFieldException;

    /**
     * Возвращает признак пустоты фильтра.
     *
     * @return {@code true}, если все поля фильтра пустые
     */
    boolean isEmpty();
}
