package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        int[] intArray = {3, 2, 7, 5, 2, 9, 1, 7, 4, 4,};
        int minValue = minValue(intArray);
        System.out.println(minValue);

        System.out.println(oddOrEven(Arrays.asList(88, 13, 23, 454, 6, 7, 8, -1)));

        System.out.println(oddOrEvenStream(Arrays.asList(88, 13, 23, 454, 6, 7, 8, -1)));
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
        /*return integers.stream().filter(integers.stream().mapToInt(Integer::intValue)
                .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 != 0).collect(Collectors.toList()); */
        // Делим стрим на 2 коллекции - четных и нечетный чисел
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0));
        //считаем кол-во элементов в коллекции с нечетными числами, если их нечетное количество - вся сумма нечетная
        return map.get(map.get(false).size() % 2 != 0);
    }

}
