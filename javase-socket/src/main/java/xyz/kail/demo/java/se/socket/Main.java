package xyz.kail.demo.java.se.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws IOException {
        Instant instant = Clock.systemUTC().instant();
        int nano = instant.getNano();
        System.out.println(nano);
        System.out.println(System.nanoTime());
    }
}
