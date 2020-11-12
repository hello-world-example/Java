# JXM MBean


JMX (Java Management Extensions) 是 Java 管理扩展，MBean(Managed Beans) 是 被管理的Beans。

- 一个 MBean 是一个被管理的 Java 对象，有点类似于 JavaBean
- 任何资源都可以被表示为 MBean
- MBean 会对外暴露一个接口，这个接口可以读取或者写入一些对象中的属性



## 暴露 MBean




### 定义MBean接口

- 接口必须以 `MBean` 结尾
-  一般方法会以 **"操作 (Operations)"** 的形式展示
-  get/set 方法会以 **"属性 (Attributes)"** 的形式展示

``` java
package xyz.kail.demo.javase.lang.management.mbean;

/**
 * 定义 MBean 接口，与一般的接口类似，**但是必须以MBean结尾**
 */
public interface HelloWorldMBean {

    /**
     * 定义 MBean 的名字
     */
    String TYPE_HELLO_WORLD = "xyz.kail.demo.jmx:type=HelloWorld";

    /**
     * 一般方法会以"操作"的形式在 JConsole 或者 JvisualVM 显示
     */
    void hello();

    void say(String name);


    /**
     * get/set 方法会以"属性"的形式在 JConsole 或者 JvisualVM 显示
     */
    String getName();

    void setName(String name);

}  
```



### 实现MBean接口

``` java
package xyz.kail.demo.javase.lang.management.mbean;

/**
 * @author kail
 */
public class HelloWorld implements HelloWorldMBean {

    private String name = "Kail";

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
        this.name = name;
    }

}  
```



### 注册 MBean

``` java
package xyz.kail.demo.javase.lang.management.mbean;

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

        // 结束程序的时候在控制台随便输入点什么
        System.in.read();
    }
}
```







## 获取本地 MBean

```java
package xyz.kail.demo.javase.lang.management.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class GetMBeanMain {

    public static void main(String[] args) throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        String[] domains = mBeanServer.getDomains();
        System.out.println(Arrays.asList(domains));

        Integer mBeanCount = mBeanServer.getMBeanCount();
        System.out.println(mBeanCount);

        // ❤ 匹配所有
        Set<ObjectName> objectNames = mBeanServer.queryNames(null, null);
        System.out.println("【All】 : " + objectNames);

        // 通配符 1
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:*"), null);
        System.out.println("【java.lang:*】 : " + objectNames);

        // 通配符 2
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:type=MemoryPool,*"), null);
        System.out.println("【java.lang:type=MemoryPool,*】 : " + objectNames);

        // 通配符 3
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:type=MemoryPool,name=PS*"), null);
        System.out.println("【java.lang:type=MemoryPool,name=PS*】 : " + objectNames);

        // 精确匹配
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:type=GarbageCollector,name=PS Scavenge"), null);
        System.out.println("【java.lang:type=GarbageCollector,name=PS Scavenge】 : " + objectNames);

        // 读取属性
        for (ObjectName objectName : objectNames) {
            MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);
            // 获取 属性 Name
            String[] attributeNames = Arrays.stream(mBeanInfo.getAttributes()).map(MBeanFeatureInfo::getName).toArray(String[]::new);
            // 批量获取属性列表
            AttributeList attributes = mBeanServer.getAttributes(objectName, attributeNames);
            Iterator<Object> iterator = attributes.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                System.out.println("    " + next.toString());
            }

        }
    }

}

```





## 获取远程 MBean

> 在启动 StartMBeanMain 的时候增加 JVM 参数
>
> - -Dcom.sun.management.jmxremote=true 
> - -Dcom.sun.management.jmxremote.port=12345 
> - -Dcom.sun.management.jmxremote.ssl=false 
> - -Dcom.sun.management.jmxremote.authenticate=false
>
> 

```java
import static xyz.kail.demo.javase.lang.management.mbean.HelloWorldMBean.TYPE_HELLO_WORLD;

public class GetRemoteMBeanMain {

  // 固定格式
  private static final String JMX_URL_TEMPLATE = "service:jmx:rmi:///jndi/rmi://%s/jmxrmi";

  public static void main(String[] args) throws IOException, MalformedObjectNameException {

    // 构造 JMX rmi 链接
    String jmxUrl = String.format(JMX_URL_TEMPLATE, "127.0.0.1:12345");

    // 链接到远程
    JMXConnector connect = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl));

    // 获取 MBean 远程链接
    MBeanServerConnection mBeanServer = connect.getMBeanServerConnection();

    // 查询 MBean
    ObjectName objectName = new ObjectName("xyz.kail.demo.jmx:type=HelloWorld");
    Set<ObjectInstance> objectInstances = mBeanServer.queryMBeans(objectName, null);

    System.out.println(objectInstances);

  }

}
```

> 
>
> [Explain JMX URL service:jmx:rmi:///jndi/rmi://](https://stackoverflow.com/questions/2768087/explain-jmx-url)
>
> 





## 调用 MBean

```java
MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);

// 获取所有的操作
MBeanOperationInfo[] operations = mBeanInfo.getOperations();

// 打印操作信息
for (MBeanOperationInfo operation : operations) {
  System.out.println(operation.toString());
}

// 调用带参数的 say 方法
mBeanServer.invoke(objectName, "say", new Object[]{"World2"}, new String[]{"java.lang.String"});

// 调用 hello 方法
mBeanServer.invoke(objectName, "hello", new Object[]{}, new String[]{});
```



## Read More

- Oracle [Monitoring and Management Using JMX Technology](https://docs.oracle.com/javase/8/docs/technotes/guides/management/agent.html)
- Oracle [Java Management Extensions (JMX)](http://docs.oracle.com/javase/8/docs/technotes/guides/jmx/index.html)
- JDK 文档
  - [ java.lang.management](https://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/management/package-summary.html)
  - [javax.management](http://tool.oschina.net/uploads/apidocs/jdk-zh/javax/management/package-summary.html)
- [MBean 和 MXBean](https://www.ibm.com/support/knowledgecenter/zh/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mxbeans.html)