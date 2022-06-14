package ru.clevertec.checkrunner;

import java.util.*;
import java.util.stream.Collectors;

public class StreamApiMain {
    public static void main(String[] args) {
        int[] array = {0, 2, -1, -5, 7, 4, 2, 1, 6, 12};
        List<String> stringList = Arrays.asList("Minsk", "Moscow", "New-York", "Berlin", "Kiev", "France");
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");
        map.put(5, "Five");
        map.put(6, "Six");

//        вывести значения >3
        Arrays.stream(array)
                .filter(value -> value > 3)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();

//        отрицательные значения заменить на 0
        Arrays.stream(array)
                .map(value -> value < 0 ? value = 0 : value)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();

//        вывести строки длина которых >=6
        stringList.stream()
                .filter(s -> s.length() >= 6)
                .forEach(System.out::println);

//        Преобразовать значения в верхний регистр, собрать в другую коллекцию
        List<String> newList = stringList.stream()
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(newList);
        stringList.stream()
                .sorted()
                .forEach(s -> System.out.print(s + " "));
        System.out.println();

//Заменить в строках букву, собрать в другую коллекцию
        List<String> newList1 = stringList.stream()
                .map(s -> s.replace("n", "N"))
                .collect(Collectors.toList());
        System.out.println(newList1);

//   Найти "Tokyo", если нет, вывести, Not Found
        String optional = stringList.stream()
                .filter(s -> s.equals("Tokyo")).findFirst()
                .orElse("Not Found");
        System.out.println(optional);
        System.out.println();

//      Вывести значения где ключ >3
        map.entrySet().stream()
                .filter(k -> k.getKey() > 3)
                .forEach(System.out::println);
        System.out.println();

//    Перемножить ключи, длины значений которых >=4
        int multy = map.entrySet().stream()
                .filter(e -> e.getValue().length() >= 4)
                .mapToInt(Map.Entry::getKey)
                .reduce(1, (x, y) -> x * y);
        System.out.println(multy);
    }
}

