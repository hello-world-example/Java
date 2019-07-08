package xyz.kail.demo.java.se.temp;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class IoFlushMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        int bufferSize = 10000;
        FileOutputStream fos = new FileOutputStream("D:\\a.txt");
        BufferedOutputStream dest = new BufferedOutputStream(fos, bufferSize);

        dest.write(1);
        dest.flush();
        TimeUnit.SECONDS.sleep(10);
    }

}
