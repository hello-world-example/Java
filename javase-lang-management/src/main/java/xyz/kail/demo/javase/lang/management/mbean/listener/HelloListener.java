package xyz.kail.demo.javase.lang.management.mbean.listener;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener {

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("SequenceNumber:" + notification.getSequenceNumber());
        System.out.println("Type:" + notification.getType());
        System.out.println("Message:" + notification.getMessage());
        System.out.println("Source:" + notification.getSource());
        System.out.println("TimeStamp:" + notification.getTimeStamp());

        if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification changeNotification = (AttributeChangeNotification) notification;
            System.out.println("OldValue:" + changeNotification.getOldValue());
            System.out.println("NewValue:" + changeNotification.getNewValue());
        }
    }
}
