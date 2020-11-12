package xyz.kail.demo.javase.lang.management.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class GetMBeanMain {

    public static void main(String[] args) throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        String[] domains = mBeanServer.getDomains();
        System.out.println(Arrays.asList(domains));

        Integer mBeanCount = mBeanServer.getMBeanCount();
        System.out.println(mBeanCount);

        // ❤ 匹配所有
        Set<ObjectName> objectNames = mBeanServer.queryNames(null, null);
        System.out.println("【All】 : " + objectNames);

        // 通配符 1
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:*"), null);
        System.out.println("【java.lang:*】 : " + objectNames);

        // 通配符 2
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:type=MemoryPool,*"), null);
        System.out.println("【java.lang:type=MemoryPool,*】 : " + objectNames);

        // 通配符 3
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:type=MemoryPool,name=PS*"), null);
        System.out.println("【java.lang:type=MemoryPool,name=PS*】 : " + objectNames);

        // 精确匹配
        objectNames = mBeanServer.queryNames(ObjectName.getInstance("java.lang:type=GarbageCollector,name=PS Scavenge"), null);
        System.out.println("【java.lang:type=GarbageCollector,name=PS Scavenge】 : " + objectNames);

        // 读取属性
        for (ObjectName objectName : objectNames) {
            MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);
            // 获取 属性 Name
            String[] attributeNames = Arrays.stream(mBeanInfo.getAttributes()).map(MBeanFeatureInfo::getName).toArray(String[]::new);
            // 批量获取属性列表
            AttributeList attributes = mBeanServer.getAttributes(objectName, attributeNames);
            Iterator<Object> iterator = attributes.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                System.out.println("    " + next.toString());
            }

        }
    }

}
