package xyz.kail.demo.javase.rmi.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义远程接口
 */
public interface HelloRemote extends Remote {

    String helloWorld() throws RemoteException;

    String sayHello(String name) throws RemoteException;

}