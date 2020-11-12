package xyz.kail.demo.javase.rmi.hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * 客户端调用
 */
public class HelloNamingClient {

    private static HelloRemote hello;

    public static void client() throws MalformedURLException, RemoteException, NotBoundException {
        // 在 RMI 注册表中查找指定对象
        hello = (HelloRemote) Naming.lookup("rmi://127.0.0.1:8888/Hello");

        String[] nameList = Naming.list("rmi://127.0.0.1:8888");
        System.out.println(Arrays.toString(nameList));

        // 调用远程对象方法
        System.out.println("client:");
        System.out.println(hello.helloWorld());
        System.out.println(hello.sayHello("神之一手"));
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        client();
    }

}