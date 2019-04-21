package xyz.kail.demo.java.se.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 动态代理类对应的调用处理程序类
 */
public class SubjectInvocationHandler3 implements MethodInterceptor {

    //代理类持有一个委托类的对象引用
    private Object delegate;

    public SubjectInvocationHandler3(Object delegate) {
        this.delegate = delegate;
    }


    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        long stime = System.currentTimeMillis();

        methodProxy.invokeSuper(o, args);

        long ftime = System.currentTimeMillis();
        System.out.println("执行任务耗时" + (ftime - stime) + "毫秒");

        return null;
    }
}