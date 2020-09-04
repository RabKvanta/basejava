package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        int[] intArray = {3, 2, 7, 5, 2, 9, 1, 7};
        int minValue = minValue(intArray);
        System.out.println(minValue);

        System.out.println(oddOrEven(Arrays.asList(88, 13, 23, 454, 6, 7, 8)));

        //  oddOrEvenStream(Arrays.asList(1, 2, 3, 4, 6, 7, 8));
    }

    public static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce((sum, s) -> sum * 10 + s).getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        int sum = 0;
        for (Integer val : integers) {
            if (val % 2 == 0) {
                evenList.add(val);
            } else {
                oddList.add(val);
            }
            sum += val;
        }
        return sum % 2 != 0 ? evenList : oddList;
    }

    public static List<Integer> oddOrEvenStream(List<Integer> integers) {
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        AtomicInteger sum = new AtomicInteger();

       /* integers.stream().forEach(x -> {
            sum.addAndGet(x);
            if (x % 2 == 0) {
                evenList.add(x);
            } else {
                oddList.add(x);
            }
        });*/

        Map<Boolean, List<Integer>> evenOddMap = integers.stream().collect(Collectors.partitioningBy(e -> e % 2 == 0));
        Map<Integer, List<Integer>> evenOdd = integers.stream().collect(Collectors.groupingBy(i -> i & 1));

        // Map<Integer, Integer> collect = integers.stream().collect(Collectors.groupingBy(i -> i & 1, Collectors.summingInt(Integer::intValue)));

        //evenList = new ArrayList<>(map.get(true));
        //oddList = new ArrayList<>(map.get(false));

        return null;
    }

}
