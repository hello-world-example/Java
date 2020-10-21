# JavaAgent



## 概述

- `JVMTI` （**JVM Tool Interface**）是 Java 虚拟机对外提供的 Native 编程接口，通过 `JVMTI`，外部进程可以获取到运行时 JVM 的诸多信息，比如 线程、GC 等
- **在 Java SE 5之前**，要实现一个 Agent 只能通过编写 Native 代码来实现
- **从 Java SE 5 开始**，可以使用 Java 的  `java.lang.instrument.Instrumentation` 来编写 Agent，但是 1.5 时 只提供了 `premain` 一种方式，即在 main 方法启动前启动一个 `-javaagent:` 代理程序
-  **Java SE 6 开始**，提供了 `agentmain` 方式，支持在运行时使用 Agent



## agent 签名

```java
// Java SE 5+
// 通过: -javaagent:agent.jar=key1=value1;key2=value2
public static void premain(String agentArgs, Instrumentation inst) { }
public static void premain(String agentArgs) { }

// Java SE 6+
// 通过: VirtualMachine.attach(pid).loadAgent("agent.jar", "value1;key2=value2")
public static void agentmain(String agentArgs, Instrumentation inst) { }
public static void agentmain(String agentArgs) { }
```



## META-INF/MANIFEST.MF

定义完 Agent 需要在打包的的时候自定义 `META-INF/MANIFEST.MF`

- `Premain-Class:`  Agent Class 全路径名，对应 `premain` 方式
- `Agent-Class:` Agent Class 全路径名，对应 `agentmain` 方式
- `Can-Redefine-Classes:` true 表示能 重新定义类，即 通过`instrumentation.redefineClasses(ClassDefinition...)` 对类的字节码进行增强
- `Can-Retransform-Classes:` true 表示能 重新加载类，提通过 `instrumentation.retransformClasses(Class<?>...)` 回归类的增强

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.1.0</version>
  <configuration>
    <archive>
      <!--自动添加META-INF/MANIFEST.MF -->
      <manifest>
        <addClasspath>true</addClasspath>
      </manifest>
      <manifestEntries>
        <Main-Class>xyz.kail.demo.javase.instrument.agent.Main</Main-Class>
        <!---->
        <Premain-Class>xyz.kail.demo.javase.instrument.agent.Agent</Premain-Class>
        <Agent-Class>xyz.kail.demo.javase.instrument.agent.Agent</Agent-Class>
        <!---->
        <Can-Redefine-Classes>true</Can-Redefine-Classes>
        <Can-Retransform-Classes>true</Can-Retransform-Classes>
      </manifestEntries>
    </archive>
  </configuration>
</plugin>
```

## 示例

### Agent.java

```java
package xyz.kail.demo.javase.instrument.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Agent {

  /**
   * 在 Java6 开始，支持在运行时使用 Agent
   */
  public static void agentmain(String args, Instrumentation instrumentation) throws UnmodifiableClassException {
    Map<String, String> params = parseArgs(args);
    if (params.isEmpty()) {
      System.err.println("缺少参数");
      return;
    }

    /*
     *  注册 Transformer
     */
    premain(args, instrumentation);


    String regex = params.get("regex");
    if (null == regex) {
      System.err.println("缺少参数 regex");
      return;
    }

    // 获取所有已加载的 Class
    Class[] allLoadedClasses = instrumentation.getAllLoadedClasses();
    for (Class clazz : allLoadedClasses) {
      // 如果类名匹配正则，重新转换类
      if (Pattern.matches(regex, clazz.getName())) {
        // Attach 时，类已经被加载，但是 instrumentation.addTransformer 是后执行的，所以需要 重新转换类
        instrumentation.retransformClasses(clazz);
      }
    }

  }


  /**
   * 在 Java5 时代，Instrument 只提供了 premain 一种方式，即在 main 方法启动前启动一个代理程序
   */
  public static void premain(String args, Instrumentation instrumentation) {
    Map<String, String> params = parseArgs(args);
    if (params.isEmpty()) {
      System.err.println("缺少参数");
      return;
    }

    /*
     * 启用打印
     */
    if (Boolean.TRUE.toString().equals(params.get("ClassToFileTransformer"))) {
      instrumentation.addTransformer(new ClassToFileTransformer(params), true);
    }
  }

  /**
   * key1=value1;key2=value2  --> {key1:value1, key2:value2}
   */
  private static Map<String, String> parseArgs(String args) {
    if (null == args) {
      return Collections.emptyMap();
    }

    Map<String, String> argsMap = new HashMap<>(8);
    String[] kvs = args.split(";");
    for (String kv : kvs) {
      String[] kvArr = kv.split("=");
      argsMap.put(kvArr[0], kvArr[1]);
    }
    return argsMap;
  }

}
```

### ClassToFileTransformer.java

```java
package xyz.kail.demo.javase.instrument.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 把加载的类，写入到文件
 */
public class ClassToFileTransformer implements ClassFileTransformer {

    Map<String, String> args;

    public ClassToFileTransformer(Map<String, String> args) {
        this.args = args;
    }

    /**
     * @param className       类名
     * @param classFileBuffer 类的字节码
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) throws IllegalClassFormatException {
        if (null != args.get("debug")) {
            System.out.println(className);
        }

        String regex = args.get("regex");
        // 如果传入正则，但是不匹配，不进行操作
        if (!(null != regex && Pattern.matches(regex, className))) {
            return classFileBuffer;
        }

        try {
            Path path = Paths.get(args.get("path") + "/agent/" + className + ".class");
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, classFileBuffer, StandardOpenOption.CREATE);
        } catch (Throwable ignored) {
            if (null != args.get("debug")) {
                ignored.printStackTrace();
            }
        }

        return classFileBuffer;
    }

}
```



## premain agent 方式

```bash
java -javaagent:javase-instrument.jar=ClassToFileTransformer=true;debug=true;regex=xyz.+;path=~/ -jar  xxx.jar
```



## agentmain attach 方式

### 增加依赖

```xml
<dependencies>
  <!-- 或 -Xbootclasspath/a:${JAVA_HOME}lib/tools.jar -->
  <dependency>
    <groupId>com.sun</groupId>
    <artifactId>tools</artifactId>
    <version>1.8</version>
    <scope>system</scope>
    <systemPath>${java.home}/../lib/tools.jar</systemPath>
  </dependency>
</dependencies>
```

### Main.java

> - IDE 中 执行 main 方式
> - 或 java -Xbootclasspath/a:${JAVA_HOME}lib/tools.jar -jar xxx.jar 运行

```java
package xyz.kail.demo.javase.instrument.agent;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws Exception {
    // /target/classes/../
    System.out.println(Main.class.getResource("/"));

    Path targetPath = Paths.get(Main.class.getResource("/").toURI()).getParent();
    System.out.println("targetPath:" + targetPath);

    //
    List<VirtualMachineDescriptor> vmDescriptors = VirtualMachine.list();
    for (VirtualMachineDescriptor vm : vmDescriptors) {
      System.out.println(vm.id() + " : " + vm.displayName());
    }

    //
    Scanner scanner = new Scanner(System.in);
    System.out.println("请输入 pid: ");
    String pid = scanner.nextLine();
    System.out.println("请输入 regex: ");
    String regex = scanner.nextLine();
    scanner.close();

    //
    VirtualMachine vm = VirtualMachine.attach(pid);
    try {
      vm.loadAgent(targetPath + "/javase-instrument.jar",
                   "" +
                   "ClassToFileTransformer=true;" +
                   "debug=true;" +
                   "regex=" + regex + ";" +
                   "path=" + targetPath
                  );
    } finally {
      vm.detach();
    }
  }
}
```



## Read More

- Code @see [Java / javase-instrument](https://github.com/hello-world-example/Java/tree/master/javase-instrument)

- [Java SE 6 新特性 - Instrumentation 新功能](https://www.ibm.com/developerworks/cn/java/j-lo-jse61/index.html)
- [深入理解 Instrument](https://www.jianshu.com/p/5c62b71fd882)
- [Java 动态调试技术原理及实践](https://mp.weixin.qq.com/s/ZlNcvwJ_swspifWTLHA92Q)

