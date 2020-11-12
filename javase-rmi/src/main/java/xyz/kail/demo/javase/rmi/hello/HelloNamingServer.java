package xyz.kail.demo.javase.rmi.hello;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 服务端绑定
 */
public class HelloNamingServer {

    static HelloRemote hello;

    public static void server() throws RemoteException, MalformedURLException, AlreadyBoundException {
        //远程对象注册表实例
        LocateRegistry.createRegistry(8888);

        hello = new HelloRemoteImp();

        //把远程对象注册到RMI注册服务器上
        Naming.bind("rmi://0.0.0.0:8888/Hello", hello);
        Naming.bind("rmi://0.0.0.0:8888/World", hello);

        System.out.println("server:对象绑定成功！");
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        server();
    }

}