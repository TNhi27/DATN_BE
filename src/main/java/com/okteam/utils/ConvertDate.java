package com.okteam.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ConvertDate {

    public static int getDay(Date date) {
        LocalDate localDate = LocalDate.parse(date.toString());

        return localDate.getDayOfMonth();
    }

    public static int getMonth(Date date) {
        LocalDate localDate = LocalDate.parse(date.toString());

        return localDate.getMonthValue();
    }

    public static int getYear(Date date) {
        LocalDate localDate = LocalDate.parse(date.toString());

        return localDate.getYear();
    }
}