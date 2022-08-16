package ru.clevertec.checkrunner.repository;

import ru.clevertec.checkrunner.model.Item;

import java.util.List;

public interface Storage<T> {
    T get(int id);
    void add(T entity);
    void update(T entity);
    void delete(int id);
    List<T> findAll();
}