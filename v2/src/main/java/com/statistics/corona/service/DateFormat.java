package com.statistics.corona.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateFormat {

    private static final String DATE_PATTERN = "dd.MM.yyy";

    public String formatLastUpdateToDate(String lastUpdate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMAN);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.GERMAN);
        LocalDate date = LocalDate.parse(lastUpdate, inputFormatter);
        return outputFormatter.format(date);
    }

    public String formatLastUpdateToDateDayOne(String lastUpdate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMAN);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.GERMAN);
        LocalDate date = LocalDate.parse(lastUpdate, inputFormatter);
        return outputFormatter.format(date);
    }

    public String formatLastUpdateToTime(String lastUpdate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMAN);
        DateTimeFormatter outputFormatterTime = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMAN);
        LocalTime time = LocalTime.parse(lastUpdate, inputFormatter);
        return outputFormatterTime.format(time);
    }

    public String formatDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.GERMAN);
        LocalDate dates = LocalDate.parse(date, inputFormatter);
        return outputFormatter.format(dates);
    }
}