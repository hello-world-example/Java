package xyz.kail.demo.javase.regex;

import java.util.regex.Pattern;

public class RegexMain {

    public static void main(String[] args) {
        System.out.println("13843838438".replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
        System.out.println("13843838438".replaceAll("(\\d{3})(?:\\d{4})(\\d{4})", "$1****$2"));
        System.out.println("13843838438".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    }

}
