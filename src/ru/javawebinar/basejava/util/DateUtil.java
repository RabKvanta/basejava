package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String toString(LocalDate localDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
        return localDate.equals(NOW)? "настоящее время" : localDate.format(dtf) ;
    }
}
