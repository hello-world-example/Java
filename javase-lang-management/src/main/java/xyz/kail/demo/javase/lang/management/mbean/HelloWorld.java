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