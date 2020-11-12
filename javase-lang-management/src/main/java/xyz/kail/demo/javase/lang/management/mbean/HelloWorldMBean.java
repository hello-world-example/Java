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