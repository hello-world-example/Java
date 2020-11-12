package xyz.kail.demo.javase.rmi.hello;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 远程接口实现
 */
public class HelloRemoteImp extends UnicastRemoteObject implements HelloRemote {

    public HelloRemoteImp() throws RemoteException {
    }

    @Override
    public String helloWorld() throws RemoteException {
        return "Hello AlphaGo!";
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello" + name + "!";
    }

}