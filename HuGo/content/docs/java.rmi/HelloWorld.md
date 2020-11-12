# RMI 入门示例



## 简介

- `RMI`  （Remote Method Invocation），允许 一个 JVM 访问 另外 JVM 中运行对象
- 提供了 Java 进程间远程通讯的机制，在 `java.rmi` 包中提供



## 架构



![](https://www.tutorialspoint.com/java_rmi/images/rmi_architecture.jpg)

- **Transport Layer** ：管理和常见 Client 和 Server 之间的链接
- **Stub** ：Client 侧 对远程对象的代理
- **Skeleton** ： Client 通过 Stub 与 Skeleton 通信，交换云对象
- **RRL(Remote Reference Layer)** ： 远程引用层，Client 对远程对象的引用层



## 调用流程

- Client 对 远程对象进行调用时，通过 Stub 接收这个操作，并将这个操作传递给 RRL
- Client 的 RRL 接收到请求后，调用 **remoteRef** 的 **invoke()** 方法，将请求传递给 Server 端 的 RRL
- Server 的 RRL 将请求传递给 **Skeleton** 即服务端的代理，最终调用 Server 端的对象
- 结果反向传递给 CLient



## 注册登记

![](https://www.tutorialspoint.com/java_rmi/images/registry.jpg)



## RMI 程序示例

### HelloRemote 定义远程接口

```java
public interface HelloRemote extends Remote {

    String helloWorld() throws RemoteException;

    String sayHello(String name) throws RemoteException;

}
```



### HelloRemoteImp 实现远程接口

```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloRemoteImp extends UnicatRemoteObject implements HelloRemote {

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
```



## HelloRegistryServer 服务端绑定

```java
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloRegistryServer {

  public static void server() throws RemoteException, AlreadyBoundException {
    // 远程对象注册表
    Registry registry = LocateRegistry.createRegistry(8888);
    // 实例化远程对象
    HelloRemote hello = new HelloRemoteImp();
    // 注册绑定远程对象
    registry.bind("/Hello", hello);
    registry.bind("/World", hello);

    System.out.println("server:对象绑定成功！");
  }

  public static void main(String[] args) throws AlreadyBoundException, RemoteException {
    server();
  }

}
```



### HelloRegsitryClient 客户端调用

```java
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class HelloRegsitryClient {

  public static void client() throws RemoteException, NotBoundException {
    // 获取注册表
    Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8888);

    // 在 RMI 注册表中查找指定对象
    HelloRemote hello = (HelloRemote) registry.lookup("/Hello");

    // 获取所有 Name 列表
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
```





## Read More

- [Java RMI Tutorial](https://www.tutorialspoint.com/java_rmi/)
- [分布式架构基础:Java RMI详解](https://www.jianshu.com/p/de85fad05dcb)