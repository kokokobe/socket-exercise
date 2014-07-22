package com.liang.java8.time;

import java.time.*;

/**
 * @author Briliang
 * @date 2014/7/22
 * Description()
 */
public class Time {
    final static Clock clock=Clock.systemUTC();
    final static LocalDate date=LocalDate.now();
    final static LocalDate dateFromClock=LocalDate.now(clock);
    final static LocalTime time=LocalTime.now();
    final static LocalTime timeFromClock=LocalTime.now(clock);
    final static LocalDateTime dateTime=LocalDateTime.now();
    final static LocalDateTime dateTimeFromClock=LocalDateTime.now(clock);
    public static void main(String[] args) {
        System.out.println(clock.instant());
        System.out.println(clock.millis());
        System.out.println(date);
        System.out.println(dateFromClock);
        System.out.println(time);
        System.out.println(timeFromClock);
        System.out.println(dateTime);
        System.out.println(dateTimeFromClock);
        final ZonedDateTime zonedDateTime=ZonedDateTime.now();
        final ZonedDateTime zoneDateTimeFromClock=ZonedDateTime.now(clock);
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );
        System.out.println(zonedDateTime);
        System.out.println(zoneDateTimeFromClock);
        System.out.println(zonedDatetimeFromZone);
        /**
         * Duration类：在秒与纳秒级别上的一段时间
         */
        final LocalDateTime from=LocalDateTime.of(2014,Month.APRIL,16,0,0,0);
        final LocalDateTime to=LocalDateTime.of(2015,Month.APRIL,16,23,59,59);
        final Duration duration=Duration.between(from,to);
        System.out.println("Duration in days:"+duration.toDays());
        System.out.println("Duration in hours:"+duration.toHours());
    }
}
