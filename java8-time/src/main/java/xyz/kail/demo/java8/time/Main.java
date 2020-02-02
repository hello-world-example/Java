package xyz.kail.demo.java8.time;

import java.time.*;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        System.out.println(ZoneId.getAvailableZoneIds());

        System.out.println(Clock.system(ZoneId.systemDefault()).instant());


    }

}
