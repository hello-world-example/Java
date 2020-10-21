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