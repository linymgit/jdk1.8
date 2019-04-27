package com.forrily.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * @author linyimin
 * jdk1.8时间处理
 */
public class App {
    public static void main(String[] args) {

        timezonesEt();
    }

    /**
     * 时间和日期
     */
    static void clockEt(){
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Instant instant = clock.instant();
        Date date = Date.from(instant);
    }

    /**
     * 时区
     */
    static void timezonesEt(){
        System.out.println(ZoneId.getAvailableZoneIds());

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

    }

    /**
     * 本地时间表示了一个独一无二的时间，例如：2014-03-11。这个时间是不可变的，与LocalTime是同源的。
     * 通过加减日，月，年等指标来计算新的日期,每一次操作都会返回一个新的时间对象。
     */
    static void localDateEt(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        LocalDate independenceDay = LocalDate.of(2019, Month.APRIL, 27);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);
    }

    /**
     * 字符串转日期
     */
    static void parseLocalDateEt(){
        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);

        LocalDate xmas = LocalDate.parse("27.4.2019", germanFormatter);
        System.out.println(xmas);
    }

    /**
     * 日期时间
     */
    static void localDateTimeEt(){
        LocalDateTime sylvester = LocalDateTime.of(2019, Month.APRIL, 27, 16, 44, 59);

        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek);

        Month month = sylvester.getMonth();
        System.out.println(month);

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);
    }

    /**
     * LocalDateTime Transform into Date
     * @param localDateTime
     */
    static void timeConvers(LocalDateTime localDateTime){
        Instant instant = localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);
    }

    /**
     * 字符串转日期时间
     */
    static void strToLocalDateTime(){
        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern("MMM dd, yyyy - HH:mm");

        LocalDateTime parsed = LocalDateTime.parse("Apr 27, 2019 - 16:48", formatter);
        String string = formatter.format(parsed);
        System.out.println(string);
    }
}
