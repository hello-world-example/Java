# SecurityManager





## 简单理解

- JDK 针对一些敏感的资源操作，通过 `Security` 机制，添加了很多埋点
- 权限通过 配置文件 `$JAVA_HOME/jre/lib/security/java.policy` 进行配置
- 配置文件的匹配 通过 `java.security.Permission` 的各种子类实现
- 具体的校验操作 通过 `java.security.AccessController.checkPermission(perm)` 处理
- `SecurityManager` 对 `AccessController` 进行了封装了，通过 `checkXXX()` 调用
- `SecurityManager` 的获取方式一般通过  `System.getSecurityManager()` 获取，使用的时候一定要判空，因为 安全机制不一定开启，如果没有开启，返回值是 `null`



## 示例埋点代码

### Runtime.getRuntime().exit | System.exit 退出 JVM

```java
public void exit(int status) {
    SecurityManager security = System.getSecurityManager();
    if (security != null) {
        // 不允许调用 System.exit
        security.checkExit(status);
    }
    Shutdown.exit(status);
}
```

### System.setProperty 设置系统属性

```java
public static String setProperty(String key, String value) {
  checkKey(key);
  SecurityManager sm = getSecurityManager();
  if (sm != null) {    
   // 指定的系统属性是否可写
   sm.checkPermission(new PropertyPermission(key, SecurityConstants.PROPERTY_WRITE_ACTION));
  }

  return (String) props.setProperty(key, value);
}
```

### Thread.stop

```java
@Deprecated
public final void stop() {
    SecurityManager security = System.getSecurityManager();
    if (security != null) {
        checkAccess();
        if (this != Thread.currentThread()) {
            // 非当前线程不允许停止
            security.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
        }
    }
    // A zero status value corresponds to "NEW", it can't change to
    // not-NEW because we hold the lock.
    if (threadStatus != 0) {
        resume(); // Wake up thread if it was suspended; no-op otherwise
    }

    // The VM can handle all thread states
    stop0(new ThreadDeath());
}
```



## 开启 SecurityManager

默认 SecurityManager 是关闭的，可以通过以下方式开启

增加启动参数 `-Djava.security.manager` 或 程序中 启动 `System.setSecurityManager(new SecurityManager());`



## Read More

- [Java安全：SecurityManager与AccessController](https://juejin.im/post/5b693511e51d45195113866a)