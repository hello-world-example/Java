package xyz.kail.demo.java.se.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws IOException {
        DatagramSocket udp = new DatagramSocket();
        udp.connect(InetAddress.getByName("localhost"), 80);

        System.out.println(udp);
        System.out.println(udp.isBound());
        System.out.println(udp.isClosed());
        System.out.println(udp.isConnected());

        udp.send(packet("Hello1"));
        udp.send(packet("Hello2"));
        udp.send(packet("Hello3"));
    }

    private static DatagramPacket packet(String data) {
        byte[] d = data.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(d, 0, d.length);
    }
}
