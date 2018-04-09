package xyz.kail.javase.demo.proxy;

import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * 动态代理类对应的调用处理程序类
 */
public class SubjectInvocationHandler2 implements InvocationHandler {

    //代理类持有一个委托类的对象引用  
    private Object delegate;

    public SubjectInvocationHandler2(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long stime = System.currentTimeMillis();

        method.invoke(delegate, args);

        long ftime = System.currentTimeMillis();
        System.out.println("执行任务耗时" + (ftime - stime) + "毫秒");

        return null;
    }

}