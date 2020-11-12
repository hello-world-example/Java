package xyz.kail.demo.javase.rmi.hello;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 * 客户端调用
 */
public class HelloRegsitryClient {

    public static void client() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8888);

        // 在 RMI 注册表中查找指定对象
        HelloRemote hello = (HelloRemote) registry.lookup("/Hello");

        // Name 列表
        String[] nameList = registry.list();
        System.out.println(Arrays.toString(nameList));

        // 调用远程对象方法
        System.out.println("client:");
        System.out.println(hello.helloWorld());
        System.out.println(hello.sayHello(" Kail"));
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        client();
    }

}