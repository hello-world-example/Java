package xyz.kail.demo.javase.lang.management.mbean;

import javax.management.AttributeChangeNotification;
import javax.management.NotificationBroadcasterSupport;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kail
 */
public class HelloWorld extends NotificationBroadcasterSupport implements HelloWorldMBean {

    private String name = "Kail";

    private AtomicLong sequenceNumber = new AtomicLong(1);


    /**
     * 打印属性名
     */
    @Override
    public void hello() {
        System.out.println("Hello World ：" + this.name);
    }

    @Override
    public void say(String name) {
        System.out.println("Hello " + name + " !");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.sendNotification("name", String.class.getName(), this.name, name);

        this.name = name;
    }

    private void sendNotification(String name, String type, String oldData, String newData) {
        super.sendNotification(new AttributeChangeNotification(
                this,                                     // 事件源，一直传递到 java.util.EventObject 的 source
                sequenceNumber.getAndIncrement(),                 // 通知序号，标识每次通知的计数器
                System.currentTimeMillis(),                       // 通知发出的时间戳
                AttributeChangeNotification.ATTRIBUTE_CHANGE,     // 通知发送的message
                name,                                             // 被修改属性名
                type,                                             // 被修改属性类型
                oldData,                                          // 被修改属性 老值
                newData                                           // 被修改属性 新值
        ));
    }

}  