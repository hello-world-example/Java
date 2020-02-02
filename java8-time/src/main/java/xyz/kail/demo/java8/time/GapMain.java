package xyz.kail.demo.java8.time;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class GapMain {

    public static void main(String[] args) {
        LocalDate birthday = LocalDate.of(1992, Month.JULY, 23);
        LocalDate today = LocalDate.now();

// P27y6m10d
        Period p = Period.between(birthday, today);
        System.out.println("岁 : " + p.getYears());
        System.out.println("月 : " + p.getMonths());
        System.out.println("日 : " + p.getDays());
        System.out.println("总月数 : " + p.toTotalMonths());
        System.out.println("总天数 : " + ChronoUnit.DAYS.between(birthday, today));

        System.out.println(p.getChronology());


    }

}
