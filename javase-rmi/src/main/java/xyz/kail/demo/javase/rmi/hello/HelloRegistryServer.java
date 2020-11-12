package xyz.kail.demo.javase.rmi.hello;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 服务端绑定
 */
public class HelloRegistryServer {

    public static void server() throws RemoteException, AlreadyBoundException {
        //远程对象注册表实例
        Registry registry = LocateRegistry.createRegistry(8888);

        HelloRemote hello = new HelloRemoteImp();

        registry.bind("/Hello", hello);
        registry.bind("/World", hello);

        System.out.println("server:对象绑定成功！");
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        server();
    }

}