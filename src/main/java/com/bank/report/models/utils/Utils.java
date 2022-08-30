package com.bank.report.models.utils;

import com.bank.report.controller.ReportRestControllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    public static boolean Between(int variable, int minValueInclusive, int maxValueInclusive) {
        return variable >= minValueInclusive && variable <= maxValueInclusive;
    }

    public static boolean BetweenDates(LocalDateTime date, LocalDateTime minDate, LocalDateTime maxDate)
    {
        log.info("[INI] BetweenDates");
        log.info("date : " + date);
        log.info("minDate : " + minDate);
        log.info("maxDate : " + maxDate);
        log.info("[END] BetweenDates");

        var year = Between(date.getYear(),minDate.getYear(), maxDate.getYear());
        var month = Between(date.getMonthValue(),minDate.getMonthValue(),maxDate.getMonthValue());
        var day = Between(date.getDayOfMonth(),minDate.getDayOfMonth(), maxDate.getDayOfMonth());

        log.info("year : " + year);
        log.info("month : " + month);
        log.info("day : " + day);

        return year && month && day;
    }

    public static LocalDateTime parseLocalDate(String date)
    {
        log.info("[INI] LocalDateTime");
        log.info("DATE : " + date);
        DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        log.info("Result : " + LocalDateTime.parse(date, DATEFORMATTER).toString());
        log.info("[END] LocalDateTime");

        return LocalDateTime.parse(date, DATEFORMATTER);
    }
}
