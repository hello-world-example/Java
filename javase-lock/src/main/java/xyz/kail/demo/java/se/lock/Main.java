package xyz.kail.demo.java.se.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class    Main {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();

        Condition condition = lock.newCondition();



        lock.unlock();
    }

}
