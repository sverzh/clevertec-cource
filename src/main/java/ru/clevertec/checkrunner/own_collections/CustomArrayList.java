package ru.clevertec.checkrunner.own_collections;

import java.lang.reflect.Array;
import java.util.*;

public class CustomArrayList<T> implements List<T> {
    private final int ARRAY_SIZE = 5;
    private Object[] array = new Object[ARRAY_SIZE];
    private int maxSize;
    private int counter = 0;

    @Override
    public Iterator<T> getIterator() {
        return new Iterator<T>() {
            int current = 0;

            @Override
            public boolean hasNext() {
                if (current == counter) {
                    return false;
                }
                return true;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(current++);
            }
        };
    }


    @Override
    public void setMaxSize(Integer maxSize) {
        Object[] newArray = new Object[maxSize];
        if (maxSize < counter) {
            counter = maxSize;
            this.maxSize = maxSize;
        }
        for (int i = 0; i < counter; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    @Override
    public void add(T element) {
        if (counter >= array.length - 1 && maxSize == 0) {
            setMaxSize(array.length * 2);
        }
        array[counter] = element;
        counter++;
    }

    @Override
    public void addAll(Collection<T> collection) {
        T[] source = (T[]) collection.toArray();
        addAll(source);
    }

    @Override
    public void addAll(T[] sourceArray) {
        for (int i = 0; i < sourceArray.length; i++) {
            add(sourceArray[i]);
        }
    }

    @Override
    public T set(Integer index, T element) {
        T oldElement = get(index);
        array[index] = element;
        return oldElement;
    }

    @Override
    public T remove(Integer index) {
        Object oldElement = array[index];
        for (int i = index; i < counter; i++)
            array[i] = array[i + 1];
        array[counter] = null;
        counter--;
        return (T) oldElement;
    }

    @Override
    public void clear() {
        for (int i = 0; i < counter; i++) {
            array[i] = null;
        }
        counter = 0;
    }

    @Override
    public Integer find(T element) {
        for (int i = 0; i < counter; i++) {
            if (element.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T get(Integer index) {
        return (T) array[index];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < array.length)
            return (T[]) Arrays.copyOf(array, counter, a.getClass());
        System.arraycopy(array, 0, a, 0, array.length);
        if (a.length > array.length)
            a[array.length] = null;
        return a;
    }


    @Override
    public Integer size() {
        return counter;
    }

    @Override
    public void trim() {
        for (int i = 0; i < counter; i++) {
            if (array[i] == null) {
                remove(i);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < counter; i++) {
            stringBuilder.append(array[i]);
            if (i != counter - 1) {
                stringBuilder.append(", ");
            }
        }
        return "[" +
                stringBuilder + ']';
    }
}
