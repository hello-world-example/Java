package xyz.kail.demo.javase.lang.management.mbean;

import xyz.kail.demo.javase.lang.management.mbean.listener.HelloListener;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author kail
 */
public class StartMBeanMain {

    public static void main(String[] args) throws Exception {
        // 获取 MBeanServer
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        // 新建MBean ObjectName, 在MBeanServer里标识注册的MBean
        ObjectName objectName = new ObjectName(HelloWorldMBean.TYPE_HELLO_WORLD);

        // 创建MBean
        HelloWorldMBean helloWorldMBean = new HelloWorld();

        // 注册 MBean
        mBeanServer.registerMBean(helloWorldMBean, objectName);

        // 增加监听器
        mBeanServer.addNotificationListener(objectName, new HelloListener(), null, null);

        // success
        System.out.println("MBean 注册成功");

        // 结束程序的时候在控制台随便输入点什么
        System.in.read();
    }
}