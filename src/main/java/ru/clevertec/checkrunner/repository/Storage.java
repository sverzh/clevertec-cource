package ru.clevertec.checkrunner.repository;

import java.util.List;

public interface Storage<T> {
    T findById(int id);
    void save(T entity);
    void update(T entity);
    void delete(int id);
    List<T> findAll(String size, String page);
}