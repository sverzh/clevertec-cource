package ru.clevertec.checkrunner;

import java.util.Arrays;
import java.util.List;

public class CollectionCopyMethod {

    public static <T> void copy(List<? super T> consumer, List<? extends T> produser) {
        for (int i = 0; i < produser.size(); i++)
            consumer.set(i, produser.get(i));
    }

    public static void main(String[] args) {
        List<Integer> source1 = Arrays.asList(1, 2);
        List<Integer> source2 = Arrays.asList(3, 4);
        CollectionCopyMethod.copy(source1, source2);

        System.out.println(source1);

    }
}
