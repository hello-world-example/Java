# MBean 变更通知



- MBean 的变更通知基于事件监听机制，主要有以下三个角色
- `NotificationBroadcaster` 事件广播者，一般需要 MBean 继承其实现 `NotificationBroadcasterSupport`
- `Notification` MBean 所发出的通知，包含对源 MBean 的引用 等其他扩展信息
- `NotificationListener` 监听器，接收变动的消息 `Notification` 



>  以下代码基于上文 [JXM MBean](../JMX-MBean) 



## HelloWorld 实现 MBean 接口

```java
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

  ...

    @Override
    public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
		// 【发送事件通知】 当修改 name 值的时候，会发送通知
    this.sendNotification("name", String.class.getName(), this.name, name);
    // 
    this.name = name;
  }

  private void sendNotification(String name, String type, String oldData, String newData) {
    super.sendNotification(new AttributeChangeNotification(
      this,                                        // 事件源，一直传递到 java.util.EventObject 的 source
      sequenceNumber.getAndIncrement(),            // 通知序号，标识每次通知的计数器
      System.currentTimeMillis(),                  // 通知发出的时间戳
      AttributeChangeNotification.ATTRIBUTE_CHANGE,// 通知发送的message
      name,                                        // 被修改属性名
      type,                                        // 被修改属性类型
      oldData,                                     // 被修改属性 老值
      newData                                      // 被修改属性 新值
    ));
  }

}  
```



## HelloListener 监听器

```java
import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener {

  @Override
  public void handleNotification(Notification notification, Object handback) {
    // 获取 消息 notification 中的内容
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

```



## 注册监听器

```java
public class StartMBeanMain {

  public static void main(String[] args) throws Exception {
    // 获取 MBeanServer
    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    // ...

    // 增加监听器
    mBeanServer.addNotificationListener(objectName, new HelloListener(), null, null);

  }
}
```



## Read More

- [MX通知模型：Notification](https://blog.csdn.net/vking_wang/article/details/8700967)

