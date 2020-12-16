package xyz.kail.demo.javase.lang.management.mbean;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Set;

/**
 * -Dcom.sun.management.jmxremote=true
 * -Dcom.sun.management.jmxremote.port=12345
 * -Dcom.sun.management.jmxremote.ssl=false
 * -Dcom.sun.management.jmxremote.authenticate=false
 *
 * java.rmi.server.hostname=
 * com.sun.management.jmxremote.rmi.port=
 * Dcom.sun.management.jmxremote.local.only=false
 */
public class GetRemoteMBeanMain {

    private static final String JMX_URL_TEMPLATE = "service:jmx:rmi:///jndi/rmi://%s/jmxrmi";

    public static void main(String[] args) throws IOException, MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, MBeanException {

        String jmxUrl = String.format(JMX_URL_TEMPLATE, "127.0.0.1:12345");
        System.out.println(jmxUrl);

        JMXConnector connect = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl));

        MBeanServerConnection mBeanServer = connect.getMBeanServerConnection();

        ObjectName objectName = new ObjectName("xyz.kail.demo.jmx:type=HelloWorld");

        Set<ObjectInstance> objectInstances = mBeanServer.queryMBeans(objectName, null);

        System.out.println(objectInstances);


        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);

        MBeanOperationInfo[] operations = mBeanInfo.getOperations();

        for (MBeanOperationInfo operation : operations) {
            System.out.println(operation.toString());
        }

        mBeanServer.invoke(objectName, "say", new Object[]{"World2"}, new String[]{"java.lang.String"});

        mBeanServer.invoke(objectName, "hello", new Object[]{}, new String[]{});


    }

}
