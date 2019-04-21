package xyz.kail.demo.java.se.proxy;

import net.sf.cglib.proxy.Enhancer;
import xyz.kail.demo.java.se.proxy.subject.RealSubject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by kail on 2018/4/8.
 */
public class Main {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        RealSubject realSubject = new RealSubject();

//        SubjectInvocationHandler2 subject = new SubjectInvocationHandler2(realSubject);
        SubjectInvocationHandler3 subject = new SubjectInvocationHandler3(realSubject);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(realSubject.getClass());
        enhancer.setCallback(subject);
        RealSubject proxySubject = (RealSubject) enhancer.create();

        System.out.println(proxySubject.getClass().getSuperclass()); // RealSubject

        proxySubject.dealTask("asd");
        System.out.println();
//        proxySubject.dealTask2();

        System.in.read();
    }

}
