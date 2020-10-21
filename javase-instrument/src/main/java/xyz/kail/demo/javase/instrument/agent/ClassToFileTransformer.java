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
