package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    private static String nowString = "настоящее время";

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String toString(LocalDate localDate){
        return localDate.equals(NOW)? nowString : localDate.format(DATE_TIME_FORMATTER) ;
    }
    public static LocalDate parse(String date){
        if (date.equals(nowString)) return NOW;
        YearMonth yearMonth = YearMonth.parse(date, DATE_TIME_FORMATTER);
        return of(yearMonth.getYear(),yearMonth.getMonth());
    }
}
