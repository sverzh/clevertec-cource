package ru.clevertec.checkrunner;

import ru.clevertec.checkrunner.own_collections.CustomArrayList;
import ru.clevertec.checkrunner.own_collections.Iterator;
import ru.clevertec.checkrunner.own_collections.List;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomArrayListMain {
    public static void main(String[] args) {
        List<String> list = new CustomArrayList<>();
        java.util.List<String> list1 = Arrays.asList("asdf", "asdf", "asdfasdf");
        java.util.List<String> collection = new ArrayList<>();
        collection.add("aaa");
        collection.add("bbb");
        list.add("1");
        list.add("3");
        list.add("3");
        list.add(null);
        list.add("9");
        list.add("6");
        list.add("9");
        list.add("7");
        list.add("12");
        System.out.println("Исходный лист после добавления");
        System.out.println(list);
        System.out.println("работа итератора");
        Iterator<String> iterator = list.getIterator();
        while (iterator.hasNext()) {
            System.out.printf(iterator.next() + " ");
        }

        System.out.println();
        System.out.println("setMaxSize(15)");
        list.setMaxSize(15);
        System.out.println(list);
        System.out.println("setMaxSize(6)");
        list.setMaxSize(6);
        System.out.println(list);
        list.setMaxSize(12);

        System.out.println("setMaxSize(15)");
        System.out.println(list);

        System.out.println("addAll() добавление массива -{\"asdf\", \"asdf\", \"asdfasdf\"}");
        list.addAll(list1);
        System.out.println(list);

        System.out.println("addAll() добавление коллекции -{\"aaa\", \"bbb\"}");
        list.addAll(collection);
        System.out.println(list);

        System.out.println("set() установка нового значения элемента с индексом - 1");
        list.set(1, "first");
        System.out.println(list);

        System.out.println("remove ()- удаление элемента с индексом - 2");
        list.remove(2);
        System.out.println(list);

        System.out.println("find() поиск индеса первого вхождения элемента \"9\"");
        int findIndex = list.find("9");
        System.out.println(findIndex);

        System.out.println("get() получение значения элемента с индексом 3");
        String getElement = list.get(3);
        System.out.println(getElement);

        System.out.println("size() получение размера листа");
        int sizeList = list.size();
        System.out.println(sizeList);

        System.out.println("trim() удаление null значений из листа");
        list.trim();
        System.out.println(list);
        String[] newArray = list.toArray(new String[0]);
        System.out.println("toString() получение массива из коллекции");
        for (int i = 0; i <newArray.length ; i++) {
            System.out.printf(newArray[i]);
            if (i!= newArray.length){
                System.out.printf(" ");
            }
        }
        System.out.println();
        System.out.println("clear() очистка листа");
        list.clear();
        System.out.println(list);
    }
}
