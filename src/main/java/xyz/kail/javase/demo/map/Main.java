package xyz.kail.javase.demo.map;

import java.util.ArrayList;

/**
 * Created by kail on 2018/5/13.
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<String> v = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    v.add(String.valueOf(i));
                }
            }
        }).start();



        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Integer i = 0; i < Integer.MAX_VALUE; i++) {
                    if (i<v.size()){
                        v.remove(i);
                    }
                }
            }
        }).start();
    }
}
