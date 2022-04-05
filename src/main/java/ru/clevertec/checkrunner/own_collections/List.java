package ru.clevertec.checkrunner.own_collections;

import java.util.Collection;

public interface List<T> {
    Iterator<T> getIterator();

    void setMaxSize(Integer maxSize);

    void add(T element);

    void addAll(Collection<T> collection);

    void addAll(T[] array);

    T set(Integer index, T element);

    T remove(Integer index);

    void clear();

    Integer find(T element);

    T get(Integer index);

    <T> T[] toArray(T[] a);

    Integer size();

    void trim();

}
