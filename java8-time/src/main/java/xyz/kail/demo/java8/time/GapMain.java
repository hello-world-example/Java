package xyz.kail.demo.java8.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class GapMain {

    public static void main(String[] args) {
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(System.currentTimeMillis());

        Instant.now().getSecond()

    }

}
